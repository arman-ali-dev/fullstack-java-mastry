# Problem

Lesson 3 ke controller mein hum sirf aaya hua request wapas broadcast kar rahe the. Real chat mein ye galat hai, kyunki:

- message DB mein save nahi hua
- pata nahi sender asli hai ya nakli
- pata nahi user us room ka member hai ya nahi

Ab ek proper flow banana hai: check, save, phir broadcast.

### Flow

```java
A ──SEND /app/chat.send──▶ Controller
                              │
                              ▼
                        ChatService.saveMessage()   ← @Transactional
                          1. sender nikalo (Principal se)
                          2. room dhundo
                          3. member hai ya nahi check karo
                          4. DB mein save karo
                          5. MessageResponse (DTO) banao
                              │
                          COMMIT ho gaya ✓
                              │
                              ▼
                        Controller: convertAndSend("/topic/room.5", response)
                              │
                              ▼
                        Broker ──▶ A, B, C
```

### 4 rules

1. Sender payload se nahi, Principal se lo.
   - Agar client ke bheje JSON mein senderId loge, to koi bhi senderId: 7 likh ke kisi aur ke naam se message bhej dega. Principal server banata hai (JWT se, Lesson 6), isliye wo nakli nahi ho sakta.
2. Membership check server par karo.
   - Server check karta hai: user ya to ADMIN hai ya us project ka member.
3. Broadcast DB commit ke baad karo.
4. Entity nahi, DTO broadcast karo.
   - Message entity ke andar sender (poora User) hai, aur User mein password hash, email jaisi cheezein hain. Entity bhejoge to ye sab sabko chala jayega.

### Code

```java
@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageResponse saveMessage(SendMessageRequest req, String email) {

        // 1. Sender (Principal se aaya email)
        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 2. Room
        ChatRoom room = chatRoomRepository.findById(req.roomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        // 3. Access check: ADMIN ya project member
        boolean isAdmin = "ADMIN".equals(String.valueOf(sender.getRole()));
        boolean isMember = room.getProject().getMembers().stream()
                .anyMatch(m -> m.getId().equals(sender.getId()));
        if (!isAdmin && !isMember) {
            throw new AccessDeniedException("You are not a member of this room");
        }

        // 4. Basic validation
        if (req.content() == null || req.content().isBlank()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (req.type() == MessageType.TEXT && req.content().length() > 2000) {
            throw new IllegalArgumentException("Message too long");
        }

        // 5. Save
        Message m = new Message();
        m.setChatRoom(room);
        m.setSender(sender);
        m.setType(req.type() != null ? req.type() : MessageType.TEXT);
        m.setContent(req.content());
        m.setCaption(req.caption());
        m.setFileName(req.fileName());
        messageRepository.save(m);       // sentAt @PrePersist se set hota hai

        // 6. DTO transaction ke andar banao (lazy fields yahin load ho sakte hain)
        return new MessageResponse(
                m.getId(),
                room.getId(),
                m.getType(),
                m.getContent(),
                m.getCaption(),
                m.getFileName(),
                new SenderDto(sender.getId(), sender.getFullName(), sender.getProfileImage()),
                m.getSentAt()
        );
    }
}
```

```java
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void send(@Payload SendMessageRequest req, Principal principal) {
        MessageResponse saved = chatService.saveMessage(req, principal.getName());   // commit yahan ho jata hai
        messagingTemplate.convertAndSend("/topic/room." + saved.roomId(), saved);    // phir broadcast
    }
}
```
