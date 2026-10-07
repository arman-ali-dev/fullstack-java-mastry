# STOMP kyun chahiye?

### (a) Raw WebSocket ki problem

WebSocket sirf ek pipe hai. Usme text ya bytes jaate hain, bas. Usko ye nahi pata:

- kaun sa user kis room mein hai
- message kis room ke members ko jana chahiye
- kaun subscribe hai

Ye sab tumhe khud likhna padta. Sab clients ki list rakhna, room-wise group banana, loop chala ke bhejna. Har project mein yahi dobara.

### (b) STOMP kya hai

STOMP ek chhota text protocol hai jo WebSocket ke upar chalta hai. Ye pub/sub ka rule deta hai:

- Client kisi destination ko subscribe karta hai.
- Koi us destination par message publish kare, to saare subscribers ko mil jata hai.

Spring ke paas STOMP ka built-in support hai, to routing aur subscribers ki list Spring sambhalta hai.

### (c) Frames

STOMP mein har cheez ek frame hai. Mukhya frames:

1. CONNECT = Connection shuru, yahin JWT token bhejoge
2. SUBSCRIBE = Kisi destination ko sunna shuru
3. SEND = Server ko message bhejna
4. MESSAGE = Server se client ko aaya message
5. DISCONNECT = Band karna

Ek asli frame aisa dikhta hai:

```java
SEND
destination:/app/chat.send

{"roomId":5,"content":"hi"}
```

Pehli line frame ka naam, phir headers, khali line, phir body.

### (d) Destinations

Destination ek address jaisa string hai. Spring mein 2 main prefix hote hain:

1. /app/... Client → server Tumhare Java method tak jata hai
2. /topic/... Server → clients Sab subscribers ko broadcast

Chat mein har room ka apna topic hota hai, jaise /topic/room.5.

### flow

```java
B ──SUBSCRIBE /topic/room.5──▶ Server
C ──SUBSCRIBE /topic/room.5──▶ Server

A ──SEND /app/chat.send──▶ Server
        (Java method chalta hai,
         message save hota hai)

Server ──MESSAGE /topic/room.5──▶ A, B, C
```

Dhyan do: A ne /app/chat.send par bheja, aur server ne /topic/room.5 par broadcast kiya. Dono alag destinations hain.

### Summary

- Raw WebSocket mein routing aur groups khud banane padte hain.
- STOMP pub/sub deta hai: subscribe karo, publish karo, baaki Spring sambhalta hai.
- /app/... = server ke method ke liye, /topic/... = broadcast ke liye.
- Room ka matlab = ek topic, jaise /topic/room.5.
