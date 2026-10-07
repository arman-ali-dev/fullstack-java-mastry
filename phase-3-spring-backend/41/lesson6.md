# Problem

Abhi tak ka setup kaisa hai, dekho:

1. Koi bhi /ws se connect ho sakta hai, login ke bina.
2. Koi bhi /topic/room.5 ko subscribe karke us room ki chat padh sakta hai.
3. Koi bhi /app/chat.send par kuch bhi bhej sakta hai.
4. Lesson 3 ka khatra: koi /app ko chhod ke seedha /topic/room.5 par SEND kare, to message sabko chala jata hai, tumhare Java code se guzre bina.
5. Lesson 5 mein Principal use kiya, par abhi wo null hoga.

## JWT kahan bhejein

Normal REST mein JWT Authorization header mein jata hai aur ek filter har request par check karta hai. WebSocket mein 2 problems hain:

- Filter har frame par nahi chalta. HTTP request sirf shuru ke handshake ki hoti hai. Uske baad sab STOMP frames hain, HTTP requests nahi.
- Browser ka WebSocket API custom headers set karne nahi deta. Token ko URL mein (/ws?token=...) dena bhi galat hai, kyunki URLs logs mein save hote hain aur leak ho sakte hain.

Solution: STOMP ka CONNECT frame. Isme header ke taur par token jata hai:

```java
CONNECT
Authorization:Bearer eyJhbGciOi...
```

Aur check karne ke liye Lesson 3 wala clientInboundChannel use hoga, kyunki har frame wahi se guzarta hai. Wahan ek interceptor lagayenge.

### 3 checkpoints

1. Frame CONNECT : Kya check karna hai JWT valid hai? Valid hai to Principal set karo
2. Frame SUBSCRIBE : Ye user us room ka member hai?
3. SEND : Destination /app/... hi hai?

```java
Frame ──▶ clientInboundChannel ──▶ [Interceptor]
                                      │
                    CONNECT   → token verify, Principal set
                    SUBSCRIBE → room member hai?
                    SEND      → /app/... hai?
                                      │
                         fail ho to exception ──▶ client ko ERROR frame,
                                                  connection band
```

### Code

Pehle membership check ko ek alag class mein nikaalte hain, kyunki ab ye 2 jagah chahiye (interceptor aur ChatService).

```java
@Service
@RequiredArgsConstructor
public class RoomAccessService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)   // project.getMembers() lazy ho sakta hai, isliye transaction chahiye
    public boolean canAccess(String email, Long roomId) {
        User user = userRepository.findByEmail(email).orElse(null);
        ChatRoom room = chatRoomRepository.findById(roomId).orElse(null);
        if (user == null || room == null) return false;

        if ("ADMIN".equals(String.valueOf(user.getRole()))) return true;

        return room.getProject().getMembers().stream()
                .anyMatch(m -> m.getId().equals(user.getId()));
    }
}
```

#### StompAuthInterceptor

```java
@Component
@RequiredArgsConstructor
public class StompAuthInterceptor implements ChannelInterceptor {

    private static final Pattern ROOM_TOPIC = Pattern.compile("^/topic/room\\.(\\d+)$");

    private final JwtUtil jwtUtil;                       // tumhari existing class
    private final UserDetailsService userDetailsService; // tumhari existing service
    private final RoomAccessService roomAccessService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor acc =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (acc == null || acc.getCommand() == null) return message;

        switch (acc.getCommand()) {
            case CONNECT   -> authenticate(acc);
            case SUBSCRIBE -> checkSubscribe(acc);
            case SEND      -> checkSend(acc);
            default        -> { }
        }
        return message;
    }

    private void authenticate(StompHeaderAccessor acc) {
        String header = acc.getFirstNativeHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new MessagingException("Missing token");
        }
        String token = header.substring(7);

        try {
            String email = jwtUtil.extractUsername(token);          // apne method ka naam lagao
            UserDetails user = userDetailsService.loadUserByUsername(email);
            if (!jwtUtil.isTokenValid(token, user)) {               // apne method ka naam lagao
                throw new MessagingException("Invalid token");
            }
            acc.setUser(new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()));            // yahi Principal ban jata hai
        } catch (MessagingException e) {
            throw e;
        } catch (Exception e) {
            throw new MessagingException("Invalid token");          // expired, tampered, user not found
        }
    }

    private void checkSubscribe(StompHeaderAccessor acc) {
        Principal principal = acc.getUser();
        if (principal == null) throw new MessagingException("Not authenticated");

        String dest = acc.getDestination();
        Matcher m = dest == null ? null : ROOM_TOPIC.matcher(dest);

        // abhi sirf /topic/room.{id} allowed hai, baaki sab block
        if (m == null || !m.matches()) throw new MessagingException("Destination not allowed");

        Long roomId = Long.valueOf(m.group(1));
        if (!roomAccessService.canAccess(principal.getName(), roomId)) {
            throw new MessagingException("Not a member of this room");
        }
    }

    private void checkSend(StompHeaderAccessor acc) {
        if (acc.getUser() == null) throw new MessagingException("Not authenticated");

        String dest = acc.getDestination();
        if (dest == null || !dest.startsWith("/app/")) {
            throw new MessagingException("Direct send to broker is not allowed");
        }
    }
}
```

### Spring Security config

Handshake ek normal HTTP request hai (GET /ws). Browser usme token nahi bhejta. Agar tumhare SecurityFilterChain mein .anyRequest().authenticated() hai, to handshake par hi 401/403 aa jayega aur WebSocket khulega hi nahi.
<br>
Isliye handshake ko allow karo, kyunki token ka asli check ab STOMP level par ho raha hai:

### Summary

1. WebSocket mein HTTP filter har frame par nahi chalta, isliye clientInboundChannel par interceptor lagate hain.
2. Browser WebSocket header nahi bhej sakta, isliye JWT STOMP CONNECT frame ke header mein jata hai.
3. CONNECT: token verify, Principal set. SUBSCRIBE: room member check. SEND: sirf /app/... allowed.
4. /ws/\*\* handshake ko permitAll() karna zaroori hai.
5. Membership check ek hi class (RoomAccessService) mein rakho, interceptor aur service dono wahi use karein.
