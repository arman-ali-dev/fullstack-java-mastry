# Problem

Lesson 2 mein humne dekha:

- A ne /app/chat.send par message bheja.
- B aur C ko /topic/room.5 par message mila.

Ab sawal ye hai ki Spring ke andar ye hota kaise hai? Kaun decide karta hai ki /app/... wala frame Java method ke paas jaye aur /topic/... wala subscribers ke paas?

## 3 channels

Spring ke andar 3 internal raaste hain, jinhe channel kehte hain. Har channel ke peeche ek thread pool hota hai.

1. clientInboundChannel : Client se aane wale saare frames pehle yahan aate hain

Kaun-kaun se frames hote hain (client → server):

1. CONNECT / STOMP: connection shuru karne ke liye
2. SUBSCRIBE: kisi destination (jaise /topic/chat) ko sunne ke liye
3. UNSUBSCRIBE: subscription hatane ke liye
4. SEND: message bhejne ke liye (jaise /app/chat.send)
5. DISCONNECT: connection band karne ke liye
6. ACK / NACK: message receive confirm karne ke liye (kam use hota hai)

<br>

2. brokerChannel : Server se broker tak jane wale messages

Broker = message router. Ye wo component hai jo yaad rakhta hai ki kaun-sa client kis destination ko subscribe kar chuka hai, aur jab us destination par message aata hai to use sabhi subscribers tak pahunchata hai.

3. clientOutboundChannel : Broker se clients tak jane wale messages

clientOutboundChannel se sirf server → client wale frames jaate hain. Zyada tar ye STOMP MESSAGE frames hote hain.
<br>
<br>
Broker se aaye messages: Jab kisi /topic/... ya /queue/... par message publish hota hai, broker subscribers ko MESSAGE frame bana ke is channel par daalta hai. Ye sabse common case hai.

## Controller aur Broker

Controller:

```java
@MessageMapping("/chat.send")
public void send(SendMessageRequest req) { ... }
```

Spring /app prefix lagata hai, isliye client /app/chat.send bhejta hai aur ye method chalta hai.

- Broker message save nahi karta. Save DB karega.
- Ye sirf ek server ke andar kaam karta hai.

---

Broadcast kaise karte hain: SimpMessagingTemplate se.

```java
messagingTemplate.convertAndSend("/topic/room." + roomId, message);
```

A ke message ka poora flow

1. A ──SEND /app/chat.send──▶ clientInboundChannel
2. Destination /app hai ──▶ @MessageMapping method chalta hai
3. Method message save karta hai
4. Method convertAndSend("/topic/room.5", msg) karta hai (SimpMessagingTemplate message banata hai)
5. Server ye message brokerChannel par bhejta hai
6. Broker dekhta hai: room.5 par A, B, C subscribed hain
7. clientOutboundChannel se teeno ko message mil jata hai

## Config

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:5173");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");   // client -> tumhara method
        registry.enableSimpleBroker("/topic");                // broadcast ke liye
    }
}
```

1. @EnableWebSocketMessageBroker: STOMP messaging on karta hai. Isi se clientInboundChannel, brokerChannel, clientOutboundChannel aur handlers bante hain.
2. WebSocketMessageBrokerConfigurer: interface, jisme tum apni settings override karte ho.
3. registerStompEndpoints: connection ka darwaza
   - /ws: React client pehle yahan WebSocket handshake karega
   - setApplicationDestinationPrefixes("/app"): jin frames ka destination /app/... se shuru ho, wo @MessageMapping methods ko jaate hain. Jaise /app/chat.send → @MessageMapping("/chat.send"). Prefix /app hat ke match hota hai.
   - enableSimpleBroker("/topic"): in-memory broker on karta hai. /topic/... destinations broker handle karega (subscribe aur broadcast).

---

### controller

```java
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void send(@Payload SendMessageRequest req) {
        messagingTemplate.convertAndSend("/topic/room." + req.roomId(), req);
    }
}
```
