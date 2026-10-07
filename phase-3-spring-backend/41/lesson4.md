# Problem

Lesson 3 mein humne dekha ki broker message save nahi karta. Wo sirf us waqt ke subscribers ko de deta hai.
<br>
<br>
Ab socho:

- B ka internet 10 minute ke liye gaya. Us beech aaye messages kahan hain?
- User ne page refresh kiya. Purani chat kahan se aayegi?
- Kal user wapas aaya. Kal ki chat kahan hai?

Broker ke paas kuch nahi hai. Isliye DB source of truth hai. WebSocket sirf live delivery ke liye hai, storage ke liye nahi.

### Tables

Ek message ke baare mein humein ye pata hona chahiye: kisne bheja, kis room mein, kya bheja (text ya file), kab bheja.
<br>
Tumhare project mein room pehle se project se linked hai
<br>
(selectedChatRoom.project.members), to room ka member wahi hai jo project ka member hai. Isliye abhi sirf 2 naye tables chahiye:

1. chat_rooms : Har project ka ek room
2. messages : Har message ek row

Ek message ki row aisi dikhti hai:

```java
id	chat_room_id	sender_id	type	content	                        sent_at
101	       5	        2	    TEXT	hello	                        10:30:01
102	       5	        3	    IMAGE	https://res.cloudinary.com/...	10:30:20
```

### Important decisions

1. Message ka ID auto-increment rakho, aur ordering id se karo, sentAt se nahi.
   - Do messages same millisecond mein aa sakte hain. Tab sentAt same hoga aur order tie ho jayega.
   - id hamesha badhta rehta hai, to order saaf hai.

### JPA code

```java
public enum MessageType {
    TEXT, IMAGE, VIDEO, FILE
}
```

```java
@Entity
@Table(name = "chat_rooms")
@Getter
@Setter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "project_id", unique = true)
    private Project project;

    private LocalDateTime createdAt = LocalDateTime.now();
}
```

```java
@Entity
@Table(name = "messages",)
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MessageType type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;          // text ya media ka URL

    private String caption;          // image/video ke saath (optional)
    private String fileName;         // FILE type ke liye (optional)

    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt;

    @PrePersist
    void onCreate() {
        this.sentAt = LocalDateTime.now();   // server time, client ka nahi
    }
}
```

### Summary

1. Broker save nahi karta, isliye DB source of truth hai.
2. 2 tables: chat_rooms (project se linked) aur messages.
3. Order id se, sentAt se nahi.
