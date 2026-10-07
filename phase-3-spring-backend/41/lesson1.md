# Problem to WebSocket

Aapne server ko request ki. Server ne process karke response bheja. Baat khatam.
<br>
Ab server ke paas aapko dene ke liye kuch naya aaya (jaise kisi ne aapko message bheja). Par aapne koi request nahi ki, to server aapko bata nahi sakta.

<br>

Chat mein ye bahut badi problem hai, kyunki message turant dikhna chahiye.

### Solution 1: Polling

Aap khud baar-baar poochte raho, "kuch naya aaya?" Har 2 second mein ek request.
<br>
Nayi problem: Polling mein bahut waste hai

- 1000 users x har 2 second = 500 requests per second.
- Zyada tar requests ka jawab "kuch nahi" hota.
- Message 2 second late bhi dikh sakta hai.

### Solution 2: WebSocket

Idea ye hai: ek baar connection kholo aur khula rakho. Ab jab bhi kuch naya aaye, server us khule connection par seedha bhej dega. Aapko poochna nahi padega.

<br>

Shuruat ek normal HTTP request se hoti hai jisme browser bolta hai "mujhe WebSocket par upgrade kar do". Server 101 ka response deta hai, aur uske baad wahi connection WebSocket ban jati hai.

<br>

Short mein: HTTP mein server request ke bina bol nahi sakta, polling mein baar-baar poochna padta hai, WebSocket mein connection khula rehta hai to server khud bhej deta hai.

### Summary

- HTTP mein server khud se data nahi bhej sakta.
- Polling mein baar-baar poochna padta hai, slow aur wasteful hai.
- WebSocket ek khula connection hai, dono taraf se instant data jaata hai.
- Chat ke liye WebSocket standard hai.
