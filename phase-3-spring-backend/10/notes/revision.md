In real applications, not every value should be hardcoded inside Java classes.
- If the value is hardcoded, we would need to:
1. Change the Java code
2. Recompile the project
3. Rebuild the application
4. Redeploy the application

---

1. payment provider name
2. retry count
3. timeout value
4. feature enabled/disabled flag
5. database URL
6. API key
7. server port
8. external service URL
These values are better kept outside the main business logic. This is where configuration files come in.


---

application.properties is a configuration file that uses key-value pairs. Spring Boot automatically reads this file because it follows the standard file name and location.

- The default location is: src/main/resources/application.properties
- Spring Boot automatically loads this file when the application starts.
- Spring Boot also supports YAML configuration using: appliation.yml

Both application.properties and application.yml are used for configuration. The difference is mainly in the writing style.

- it becomes part of the application build. That means it is packaged inside the final JAR file.
- So technically, if we only change this internal file in the source code, we still need to rebuild the application.
- But Spring Boot provides externalized configuration, which means these values can also be supplied or overridden from outside the packaged application.
- For example, Spring Boot can read configuration from:
1. application.properties
2. application.yml
3. environment variables
4. command-line arguments
5. system properties
6. external config files

**Keep changeable values outside Java business logic so the code remains clean and flexible.**

Externalized configuration means keeping configuration values outside the Java code.
- This makes the application easier to manage across different environments.

---

Once values are present in application.properties , we need a way to use them inside Java classes.
- One simple way is to use: @Value

- @Value is simple and useful for small cases. But imagine we have many related properties:
- If we use @Value , we need to inject every value separately:
- For grouped configuration, Spring Boot provides a cleaner option: @ConfigurationProperties
- The prefix is: @ConfigurationProperties(prefix = "payment")
- This means Spring Boot will look for all properties starting with: payment

---

- Use @Value when you need one or two simple values.
- Use @ConfigurationProperties when many related values belong to the same group.

---

In a web application, code usually runs when an HTTP request comes. But in this lecture, we are not using: web related dependencies
- So we need a way to run some code
- Spring Boot gives us two common interfaces for this:
1. CommandLineRunner
2. ApplicationRunner
