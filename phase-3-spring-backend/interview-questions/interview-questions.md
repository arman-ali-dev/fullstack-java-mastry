1. What is the difference between a client and a server?
2. What is HTTP? What is the difference between HTTP and HTTPS?
3. What does "HTTP is stateless" mean?
4. What are the main parts of an HTTP request and an HTTP response?
5. PUT vs PATCH? (And when do you use GET, POST and DELETE?)
6. Path variable vs query parameter vs request body?
7. What are HTTP status codes?
8. What is REST API?
9. What is an API?
10. What is JSON? Why is it used?
11. What is the difference between an IP address and a port number?
12. What is DNS and how does it work?
13. What is Spring Initializr?
14. What is the difference between JAR and WAR packaging?
15. What is a dependency? How are dependencies managed in a Spring Boot project?
16. What is embedded Tomcat?
17. What is a fat JAR, and why does Spring Boot use it?
18. What is the classpath?
19. What is Maven and why do we need it?
20. Maven vs Gradle?
21. What is pom.xml?
22. What is a starter dependency?
23. What are transitive dependencies?
24. Where does Maven download dependencies from?

25. What is tight coupling?
26. What is Dependency Injection?
27. What is IOC? What is the relationship between IoC and DI?
28. What are the types of DI? which is better and why?
29. What is the Spring IoC Container? What is ApplicationContext?
30. ApplicationContext vs BeanFactory
31. What is a Spring bean?
32. What is autowiring? How does Spring inject a dependency into a bean?
33. What is bean scope? What is the default scope?

34. What do @Configuration and @ComponentScan do?
35. @Component vs @Bean: when do you use each?
36. What is a BeanDefinition?
37. What happens if multiple beans of the same type exist? How do @Primary and @Qualifier help, and which one wins?
38. How does Auto-configuration work?

39. What are request, session and application scopes? Where can they be used?
40. Eager vs lazy initialization. How do you make the whole application lazy?
41. Explain the Spring bean lifecycle.
42. What are @PostConstruct and @PreDestroy? Are destroy callbacks called for prototype beans?

43. What is Spring Boot?
44. What does SpringApplication.run() do?
45. What does @SpringBootApplication contain?

46. What is externalized configuration, and why do we need it?
47. application.properties vs application.yml?
48. If the same property is defined in multiple places, which one wins?
49. What are profiles?
50. @Value vs @ConfigurationProperties: when do you use which?
51. CommandLineRunner vs ApplicationRunner?

52. What is a Servlet? Why do we need it instead of handling raw HTTP with ServerSocket?
53. What is a Servlet Container?
54. External Tomcat vs embedded Tomcat: what is the difference, and why does Spring Boot use embedded Tomcat?

55. What is Spring MVC?
56. What is DispatcherServlet? and how does it get registered in Spring Boot?
57. Explain the request flow in Spring MVC.
58. What is the role of HandlerMapping, HandlerAdapter and ViewResolver?
59. @Controller vs @RestController?
60. @Component vs @Service vs @Repository: what is the difference?
61. @PathVariable vs @RequestParam vs @RequestBody?
62. What is the role of Jackson in a Spring Boot REST API? How does @RequestMapping work at class level and method level?

63. What is an Entity? What does @Entity do?
64. What is a DTO? Why do we use it?
65. Why should we not expose the Entity directly in the API?

66. What is ResponseEntity? Why use it instead of returning the object directly?
67. What is global exception handling? How do @ControllerAdvice, @RestControllerAdvice and @ExceptionHandler work together?
68. @ControllerAdvice vs @RestControllerAdvice?
69. What is a custom exception? Why do we extend RuntimeException and not Exception?
70. What is the default error handling in Spring Boot? Why do we need our own?

71. What is a Filter? Why do we use Filters, and what are cross-cutting concerns?
72. Where does a Filter sit in the request flow?
73. What is an Interceptor?
74. Filter vs Interceptor: what is the difference, and when do you use which?
75. How do you create and register an interceptor? (HandlerInterceptor, WebMvcConfigurer, addInterceptors())

76. What is Spring AOP?
77. What are the types of advice?
78. @After vs @AfterReturning: what is the difference?
79. Why is @Around the most powerful advice?
80. JoinPoint vs ProceedingJoinPoint
81. What is a pointcut expression? and how do you reuse a pointcut with @Pointcut?
82. How does Spring AOP work internally? (proxy, JDK dynamic proxy vs CGLIB)
83. Which Spring features use AOP internally?

84. What does execution() do?
85. execution() vs within()
86. How do you combine pointcut expressions?

87. What is an annotation? How do you create a custom annotation?
88. What are meta-annotations?
89. What is a marker annotation? How is it different from an annotation?

90. What is JDBC? What is the difference between the JDBC API and a JDBC driver?
91. How does a Java application connect to MySQL?
92. What is connection pooling? and what is the default pool in Spring Boot? (HikariCP)
93. What problems does raw JDBC have?
94. What is Spring JDBC, and what does JdbcTemplate do for you?

95. What is ORM? What are the responsibilities of an ORM framework?
96. Why is Spring JDBC not enough? What manual translation work does the developer still do?
97. What is Hibernate?
98. What is JPA? What is the difference between JPA and Hibernate?
99. What are @Entity, @Id, EntityManager and EntityManagerFactory?
100. What is the difference between Spring ORM and Spring Data JPA?

101. What is a persistence context? What is the first-level cache?
102. What is EntityManager? Explain persist(), find(), merge(), remove() and flush().
103. What are the entity lifecycle states?
104. What is dirty checking? How does Hibernate detect a change and run an UPDATE without you calling update()?
105. What is a transaction? Explain ACID.
106. How does @Transactional work, and what is the self-invocation problem?
107. When does Spring roll back a transaction by default? How do you roll back for a checked exception? (rollbackFor)
108. What is @PersistenceContext?

109. Explain @OneToOne @OneToMany, @ManyToOne and @ManyToMany.
110. What is the owning side vs the inverse side of a relationship?
111. What does mappedBy do?
112. what is the difference between Unidirectional and bidirectional relationship?

113. What is cascade in JPA?
114. What is the difference between LAZY vs EAGER?
115. What is the default fetch type for @OneToMany, @ManyToMany, @ManyToOne and @OneToOne? Why are they different?
116. Why should we not use EAGER everywhere?
117. What is the N+1 problem?

118. What is Spring Data JPA? What problem does it solve?
119. What is a repository proxy?
120. What is the repository hierarchy?
121. What is query derivation? How does a method like findByNameAndAge become a query?
122. How do pagination and sorting work?
