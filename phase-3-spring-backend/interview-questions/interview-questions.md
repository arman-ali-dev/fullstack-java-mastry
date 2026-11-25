1. What is the difference between a client and a server?
2. What is HTTP? What is the difference between HTTP and HTTPS?
3. What does "HTTP is stateless" mean? How do sessions, cookies and JWT solve it?
4. What are the main parts of an HTTP request and an HTTP response?
5. PUT vs PATCH? (And when do you use GET, POST and DELETE?)
6. Path variable vs query parameter vs request body?
7. What are HTTP status codes? Explain 200, 201, 400, 401, 404 and 500.
8. What is REST API?
9. What is an API?
10. What is JSON? Why is it used?
11. What is the difference between an IP address and a port number?
12. What is DNS and how does it work?
13. What is Spring Initializr?
14. What is the difference between JAR and WAR packaging?
15. What is a dependency? How are dependencies managed in a Spring Boot project ?
16. What is embedded Tomcat?
17. What is a fat JAR, and why does Spring Boot use it?
18. What is the classpath?
19. What is Maven and why do we need it?
20. Maven vs Gradle?
21. What is pom.xml?
22. What is a starter dependency?
23. What are transitive dependencies?
24. What is the Maven lifecycle?
25. Where does Maven download dependencies from?

26. What is tight coupling?
27. What is Dependency Injection? What is the difference between Dependency Injection and the Dependency Inversion Principle?
28. What is ? What is the relationship between IoC and DI?
29. What are the types of DI? which is better and why?
30. What is the Spring IoC Container? What is ApplicationContext?
31. ApplicationContext vs BeanFactory
32. What is a Spring bean?
33. What is autowiring? How does Spring inject a dependency into a bean?
34. What is bean scope? What is the default scope?

35. What do @Configuration and @ComponentScan do?
36. @Component vs @Bean: when do you use each?
37. What is a BeanDefinition?
38. What happens if multiple beans of the same type exist? How do @Primary and @Qualifier help, and which one wins?
39. How does Auto-configuration work?

40. What are request, session and application scopes? Where can they be used?
41. Eager vs lazy initialization. How do you make the whole application lazy?
42. Explain the Spring bean lifecycle.
43. What are @PostConstruct and @PreDestroy? Are destroy callbacks called for prototype beans?

44. What is Spring Boot?
45. What does SpringApplication.run() do?
46. What does @SpringBootApplication contain?

47. What is externalized configuration, and why do we need it?
48. application.properties vs application.yml?
49. If the same property is defined in multiple places, which one wins?
50. What are profiles, and how do you use them for dev and prod?
51. @Value vs @ConfigurationProperties: when do you use which?
52. CommandLineRunner vs ApplicationRunner?

53. What is a Servlet? Why do we need it instead of handling raw HTTP with ServerSocket?
54. What is a Servlet Container? ?
55. External Tomcat vs embedded Tomcat: what is the difference, and why does Spring Boot use embedded Tomcat?

56. What is Spring MVC? ?
57. What is DispatcherServlet? and how does it get registered in Spring Boot?
58. Explain the request flow in Spring MVC.
59. What is the role of HandlerMapping, HandlerAdapter and ViewResolver?
60. @Controller vs @RestController?
61. @Component vs @Service vs @Repository: what is the difference?
62. @PathVariable vs @RequestParam vs @RequestBody?
63. What is the role of Jackson in a Spring Boot REST API? How does @RequestMapping work at class level and method level?

64. What is an Entity? What does @Entity do?
65. What is a DTO? Why do we use it?
66. Why should we not expose the Entity directly in the API?

67. What is ResponseEntity? Why use it instead of returning the object directly?
68. What is global exception handling? How do @ControllerAdvice, @RestControllerAdvice and @ExceptionHandler work together?
69. @ControllerAdvice vs @RestControllerAdvice?
70. What is a custom exception? Why do we extend RuntimeException and not Exception?
71. What is the default error handling in Spring Boot? Why do we need our own?

72. What is a Filter? Why do we use Filters, and what are cross-cutting concerns?
73. Where does a Filter sit in the request flow?
74. What is an Interceptor?
75. Filter vs Interceptor: what is the difference, and when do you use which?
76. How do you create and register an interceptor? (HandlerInterceptor, WebMvcConfigurer, addInterceptors())

77. What is Spring AOP?
78. What are the types of advice?
79. @After vs @AfterReturning: what is the difference?
80. Why is @Around the most powerful advice?
81. JoinPoint vs ProceedingJoinPoint
82. What is a pointcut expression? and how do you reuse a pointcut with @Pointcut?
83. How does Spring AOP work internally? (proxy, JDK dynamic proxy vs CGLIB)
84. Which Spring features use AOP internally?

85. What does execution() do?
86. execution() vs within()
87. How do you combine pointcut expressions?

88. What is an annotation? How do you create a custom annotation?
89. What are meta-annotations?
90. What is a marker annotation? How is it different from an annotation?

91. What is JDBC? What is the difference between the JDBC API and a JDBC driver?
92. How does a Java application connect to MySQL?
93. What is connection pooling? and what is the default pool in Spring Boot? (HikariCP)
94. What problems does raw JDBC have?
95. What is Spring JDBC, and what does JdbcTemplate do for you?

96. What is ORM? What are the responsibilities of an ORM framework?
97. Why is Spring JDBC not enough? What manual translation work does the developer still do?
98. What is Hibernate?
99. What is JPA? What is the difference between JPA and Hibernate?
100. What are @Entity, @Id, EntityManager and EntityManagerFactory?
101. What is the difference between Spring ORM and Spring Data JPA?

102. What is a persistence context? What is the first-level cache?
103. What is EntityManager? Explain persist(), find(), merge(), remove() and flush().
104. What are the entity lifecycle states?
105. What is dirty checking? How does Hibernate detect a change and run an UPDATE without you calling update()?
106. What is a transaction? Explain ACID.
107. How does @Transactional work, and what is the self-invocation problem?
108. When does Spring roll back a transaction by default? How do you roll back for a checked exception? (rollbackFor)
109. What is @PersistenceContext?

110. Explain @OneToOne @OneToMany, @ManyToOne and @ManyToMany.
111. What is the owning side vs the inverse side of a relationship?
112. What does mappedBy do?
113. what is the difference between Unidirectional and bidirectional relationship?

114. What is cascade in JPA?
115. What is the difference between LAZY vs EAGER?
116. What is the default fetch type for @OneToMany, @ManyToMany, @ManyToOne and @OneToOne? Why are they different?
117. Why should we not use EAGER everywhere?
118. What is the N+1 problem?

119. What is Spring Data JPA? What problem does it solve?
120. What is a repository proxy?
121. What is the repository hierarchy?
122. What is query derivation? How does a method like findByNameAndAge become a query?
123. How do pagination and sorting work?
