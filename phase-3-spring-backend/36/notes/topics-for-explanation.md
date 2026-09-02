1. Authentication Needs a "Source of Truth"
2. Why the Default Spring Boot User Isn't Enough
3. UserDetails Interface
4. Why Spring Security Doesn't Directly Understand Your Database
5. Identity vs Credential
6. Username vs Email as Login Identifier
7. Account Status Flags (enabled, locked, expired)
8. Disabled vs Locked
9. Account Expiration vs Credential Expiration
10. Domain User Entity vs Spring Security's UserDetails
11. Roles (Authentication vs Authorization)
12. Roles Database Structure (users, roles, user_roles)
13. UserDetailsService — The Missing Bridge
14. Why Raw Passwords Should Never Be Stored
15. Why Encryption Is Not the Right Fix for Passwords
16. Password Hashing (One-Way Transformation)
17. Why a Fast Hash (SHA-256) Isn't Enough for Passwords
18. BCrypt
19. PasswordEncoder Interface
20. encode() vs matches()
21. Salt — Why Identical Passwords Hash Differently
22. Rainbow-Table Attacks
23. Registration Flow (Request → Encode → Save)
24. Registration DTO Validation vs Password Encoding
