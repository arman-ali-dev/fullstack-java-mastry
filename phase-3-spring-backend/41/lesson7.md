# Problem

Backend ready hai. Ab React ko ye 4 kaam karne hain:

1. Server se connect hona (token ke saath)
2. Room kholne par /topic/room.{id} ko subscribe karna
3. Naya message aaye to state mein daalna
4. Message bhejna

### 4 rules

1. Ek hi STOMP client poori app mein. Alag file mein ek baar banao, sab wahi use karein.
   STOMP protocol = rules (frame ka format). STOMP client = wo code/library jo un rules ko follow karke frames banata, bhejta aur padhta hai.
2. Connection ek baar (layout level par), subscription room ke hisaab se.
3. Subscription useEffect mein, aur cleanup mein unsubscribe(). Room badle ya page chhode, to purani subscription band.
4. Messages ek jagah (Redux), aur naya message id se merge ho. Isse duplicate nahi banta.

```java
App layout:      connect (ek baar)  ──▶ connected = true
Chat page:       roomId badla ──▶ purani unsubscribe, nayi subscribe
Message aaya:    Redux mein upsert ──▶ UI re-render
Send click:      stompClient.publish(/app/chat.send)
```

### Code

Step 1: Install

```bash
npm i @stomp/stompjs
```

Step 2: .env

```bash
VITE_WS_URL=ws://localhost:8080/ws
```

Step 3: src/util/stompClient.js (ek hi client)

```js
import { Client } from "@stomp/stompjs";

export const stompClient = new Client({
  brokerURL: import.meta.env.VITE_WS_URL,
  reconnectDelay: 5000, // toot jaye to 5 sec baad khud retry
  heartbeatIncoming: 10000,
  heartbeatOutgoing: 10000,
  beforeConnect: () => {
    // har connect/reconnect par taza token
    stompClient.connectHeaders = {
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    };
  },
});

export function sendMessage({
  roomId,
  type = "TEXT",
  content,
  caption,
  fileName,
}) {
  if (!stompClient.connected) return false;
  stompClient.publish({
    destination: "/app/chat.send",
    body: JSON.stringify({ roomId, type, content, caption, fileName }),
  });
  return true;
}
```

Step 4: Redux mein ye reducers add karo

```js
// initialState mein
connected: false,
messages: [],

// reducers mein
setConnected(state, action) {
  state.connected = action.payload;
},
setMessages(state, action) {
  state.messages = action.payload;
},
clearMessages(state) {
  state.messages = [];
},
upsertMessage(state, action) {
  const m = action.payload;
  const i = state.messages.findIndex((x) => x.id === m.id);
  if (i >= 0) state.messages[i] = m;     // pehle se hai to replace
  else state.messages.push(m);           // naya hai to add
},
```

Step 5: src/hooks/useStompConnection.js (layout mein ek baar lagao)

```js
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { stompClient } from "../util/stompClient";
import { setConnected } from "../redux/member/chatSlice"; // apna path

export function useStompConnection() {
  const dispatch = useDispatch();

  useEffect(() => {
    if (!localStorage.getItem("jwt")) return; // login nahi to connect mat karo

    stompClient.onConnect = () => dispatch(setConnected(true));
    stompClient.onWebSocketClose = () => dispatch(setConnected(false));
    stompClient.onStompError = (frame) =>
      console.error("STOMP error:", frame.headers["message"]);

    stompClient.activate();

    return () => {
      stompClient.deactivate();
      dispatch(setConnected(false));
    };
  }, [dispatch]);
}
```

Step 6: src/hooks/useRoomSubscription.js

```java
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { stompClient } from "../util/stompClient";
import { upsertMessage, clearMessages } from "../redux/member/chatSlice"; // apna path

export function useRoomSubscription(roomId) {
  const dispatch = useDispatch();
  const connected = useSelector((state) => state.chat.connected);

  // room badle to purane room ke messages hata do
  useEffect(() => {
    dispatch(clearMessages());
  }, [roomId, dispatch]);

  useEffect(() => {
    if (!connected || !roomId) return;

    const sub = stompClient.subscribe(`/topic/room.${roomId}`, (frame) => {
      dispatch(upsertMessage(JSON.parse(frame.body)));
    });

    return () => {
      try {
        sub.unsubscribe();
      } catch {
        // connection pehle hi toot chuka ho to ignore
      }
    };
  }, [connected, roomId, dispatch]);
}
```

Step 7: Chat page mein use

```js
const { selectedChatRoom } = useSelector((state) => state.chatRoom);
useRoomSubscription(selectedChatRoom?.id); // id ka field apne room object ke hisaab se

const handleSend = () => {
  if (!text.trim()) return;
  const ok = sendMessage({ roomId: selectedChatRoom.id, content: text });
  if (ok) setText("");
};
```

### Summary

1. Ek hi stompClient, connection layout par ek baar, subscription room ke hisaab se.
2. Subscribe useEffect mein, cleanup mein unsubscribe().
3. Messages Redux mein, id se upsert.
4. Reconnect par subscription wapas connected state badalne se banti hai.
5. Message bhejna /app/chat.send par publish, sunna /topic/room.{id} par.
