# Spring Boot: Externalized Configuration - Complete Notes

## 1. The Problem: Hardcoded Values

In real applications, not every value should be hardcoded inside Java classes.

If the value is hardcoded, we would need to:
1. Change the Java code
2. Recompile the project
3. Rebuild the application
4. Redeploy the application

That's a lot of effort just to change one value like a timeout or a URL.

### Examples of values that shouldn't be hardcoded:
1. Payment provider name
2. Retry count
3. Timeout value
4. Feature enabled/disabled flag
5. Database URL
6. API key
7. Server port
8. External service URL

These values are better kept outside the main business logic. **This is where configuration files come in.**

---

## 2. application.properties

`application.properties` is a configuration file that uses **key-value pairs**. Spring Boot automatically reads this file because it follows the standard file name and location.

- The default location is: `src/main/resources/application.properties`
- Spring Boot automatically loads this file when the application starts.

### Example *(added — was missing)*
```properties
payment.provider=stripe
payment.retry-count=3
payment.timeout=5000
server.port=8081
```

---

## 3. application.yml

Spring Boot also supports **YAML** configuration using: `application.yml`

Both `application.properties` and `application.yml` are used for configuration. The difference is mainly in the writing style.

### Example — same values in YAML *(added — was missing)*
```yaml
payment:
  provider: stripe
  retry-count: 3
  timeout: 5000
server:
  port: 8081
```

YAML uses **indentation** to show nested structure, instead of repeating the dot-prefix (`payment.`) on every line like properties files do.

---

## 4. Externalized Configuration

Since `application.properties`/`application.yml` lives inside `src/main/resources`, it becomes part of the application build. That means it is **packaged inside the final JAR file**.

So technically, if we only change this internal file in the source code, we still need to rebuild the application.

But Spring Boot provides **externalized configuration**, which means these values can also be supplied or overridden **from outside** the packaged application.

For example, Spring Boot can read configuration from:
1. `application.properties`
2. `application.yml`
3. Environment variables
4. Command-line arguments
5. System properties
6. External config files

**Keep changeable values outside Java business logic so the code remains clean and flexible.**

**Externalized configuration** means keeping configuration values outside the Java code. This makes the application easier to manage across different environments.

### Which source wins if the same property is set in multiple places? *(added — was missing)*
Spring Boot has a defined **priority order** — sources higher up override sources lower down. A simplified, commonly-cited order (highest priority first):

1. Command-line arguments (`--server.port=9090`)
2. System properties (`-Dserver.port=9090`)
3. Environment variables
4. `application.properties` / `application.yml` (packaged inside the JAR)

This means you can ship a JAR with default values in `application.properties`, and still override any of them at deployment time without touching the code or rebuilding.

### Profile-specific configuration *(added — was missing)*
For managing different environments (dev, test, prod), Spring Boot supports **profile-specific files**:
```
application-dev.properties
application-prod.properties
```

You activate a profile using:
```properties
spring.profiles.active=dev
```
Values in the active profile's file override the base `application.properties`. This is the standard way to keep environment-specific values (like different database URLs for dev vs prod) separate and swappable.

---

## 5. Reading Values with @Value

Once values are present in `application.properties`, we need a way to use them inside Java classes.

One simple way is to use: `@Value`

### Example *(added — was missing)*
```java
@Component
public class PaymentService {
    @Value("${payment.provider}")
    private String provider;

    @Value("${payment.retry-count}")
    private int retryCount;
}
```

`@Value` is simple and useful for small cases. But imagine we have many related properties — if we use `@Value`, we need to inject every value separately, one annotation per field.

### Default values with @Value *(added — was missing)*
You can provide a fallback in case the property isn't set:
```java
@Value("${payment.timeout:3000}")
private int timeout; // uses 3000 if payment.timeout is not defined
```

---

## 6. Grouped Configuration with @ConfigurationProperties

For grouped configuration, Spring Boot provides a cleaner option: `@ConfigurationProperties`

The prefix is: `@ConfigurationProperties(prefix = "payment")`

This means Spring Boot will look for all properties starting with `payment`.

### Example *(added — was missing)*
```java
@Component
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {
    private String provider;
    private int retryCount;
    private int timeout;

    // getters and setters required for binding
}
```

Now instead of injecting each value one by one, you inject the whole grouped object:
```java
@Autowired
private PaymentProperties paymentProperties;

// paymentProperties.getProvider(), paymentProperties.getRetryCount(), etc.
```

### Important: @ConfigurationProperties needs to be registered *(added — was missing)*
For Spring to actually create and bind this class, it needs to be a recognized bean. This is usually done in one of two ways:
- Adding `@Component` on the class (as shown above), or
- Using `@EnableConfigurationProperties(PaymentProperties.class)` on a configuration class, without needing `@Component` on `PaymentProperties` itself.

### Relaxed binding *(added)*
Spring Boot is flexible about naming — a property like `retry-count` in the file automatically binds to a Java field named `retryCount` (camelCase). You don't need the names to match exactly character-for-character.

---

## 7. When to Use @Value vs @ConfigurationProperties

| | Use When |
|---|---|
| `@Value` | You need one or two simple, standalone values |
| `@ConfigurationProperties` | Many related values belong to the same group |

---

## 8. Running Code at Startup: CommandLineRunner and ApplicationRunner

In a web application, code usually runs when an HTTP request comes in. But without web-related dependencies, we need another way to run some code — for example, to test that our configuration values loaded correctly, or to run one-time startup logic.

Spring Boot gives us two common interfaces for this:
1. **CommandLineRunner**
2. **ApplicationRunner**

### CommandLineRunner *(added — was missing, doc cut off here)*
```java
@Component
public class StartupTask implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started! Raw args: " + Arrays.toString(args));
    }
}
```
`run()` receives the raw command-line arguments as a plain `String[]` array — you have to parse them yourself if you need specific flags/values.

### ApplicationRunner *(added — was missing)*
```java
@Component
public class StartupTask implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Option names: " + args.getOptionNames());
    }
}
```
`run()` receives an `ApplicationArguments` object instead of a raw array — this gives structured, easier access to parsed options (e.g. distinguishing `--key=value` style arguments from plain arguments) instead of manually parsing strings.

### Key difference *(added)*
| Interface | Argument type received |
|---|---|
| `CommandLineRunner` | Raw `String... args` |
| `ApplicationRunner` | Structured `ApplicationArguments` object |

Both run automatically, right after the Spring application context has fully started — useful for verifying config, seeding data, or running one-off startup logic. If multiple runners exist, you can control their order using `@Order`.
