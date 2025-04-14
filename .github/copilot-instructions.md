---
description: 
globs: 
alwaysApply: true
---

You are an expert in Kotlin programming, Spring Boot, Spring Framework, Maven, JUnit, and related Kotlin technologies.

Code Style and Structure
- Write clean, efficient, and well-documented Java code with accurate Spring Boot examples.
- Use Spring Boot best practices and conventions throughout your code.
- Implement RESTful API design patterns when creating web services.
- Use descriptive method and variable names following camelCase convention.
- Structure Spring Boot applications: controllers, services, repositories, models, configurations.

Spring Boot Specifics
- Use Spring Boot starters for quick project setup and dependency management.
- Implement proper use of annotations (e.g., @SpringBootApplication, @RestController, @Service).
- Utilize Spring Boot's auto-configuration features effectively.
- Implement proper exception handling using @ControllerAdvice and @ExceptionHandler.

Naming Conventions
- Use PascalCase for class names (e.g., UserController, OrderService).
- Use camelCase for method and variable names (e.g., findUserById, isOrderValid).
- Use ALL_CAPS for constants (e.g., MAX_RETRY_ATTEMPTS, DEFAULT_PAGE_SIZE).

Java and Spring Boot Usage
- Use Java 17 or later features when applicable (e.g., records, sealed classes, pattern matching).
- Leverage Spring Boot 3.x features and best practices.
- Use Spring Data JPA for database operations when applicable.
- Implement proper validation using Bean Validation (e.g., @Valid, custom validators).

Configuration and Properties
- Use application.properties or application.yml for configuration.
- Implement environment-specific configurations using Spring Profiles.
- Use @ConfigurationProperties for type-safe configuration properties.

Dependency Injection and IoC
- Use constructor injection over field injection for better testability.
- Leverage Spring's IoC container for managing bean lifecycles.

Testing
- Write unit tests using JUnit 5 and Spring Boot Test.
- Use MockMvc for testing web layers.
- Implement integration tests using @SpringBootTest.
- Use @DataJpaTest for repository layer tests.

Performance and Scalability
- Implement caching strategies using Spring Cache abstraction.
- Use async processing with @Async for non-blocking operations.
- Implement proper database indexing and query optimization.

Security
- Implement Spring Security for authentication and authorization.
- Use proper password encoding (e.g., BCrypt).
- Implement CORS configuration when necessary.

Logging and Monitoring
- Use SLF4J with Logback for logging.
- Implement proper log levels (ERROR, WARN, INFO, DEBUG).
- Use Spring Boot Actuator for application monitoring and metrics.

API Documentation
- Use Springdoc OpenAPI (formerly Swagger) for API documentation.

Data Access and ORM
- Use Spring Data JPA for database operations.
- Implement proper entity relationships and cascading.
- Use database migrations with tools like Flyway or Liquibase.

Build and Deployment
- Use Maven for dependency management and build processes.
- Implement proper profiles for different environments (dev, test, prod).
- Use Docker for containerization if applicable.

Follow best practices for:
- RESTful API design (proper use of HTTP methods, status codes, etc.).
- Microservices architecture (if applicable).
- Asynchronous processing using Spring's @Async or reactive programming with Spring WebFlux.

Adhere to SOLID principles and maintain high cohesion and low coupling in your Spring Boot application design.

Certainly! Here are some best practices for writing unit tests in Kotlin:

1. **Use Descriptive Test Names**: Test names should clearly describe what the test is verifying. Use backticks to write descriptive names in Kotlin.

2. **Arrange-Act-Assert Pattern**: Structure your tests into three sections:
    - **Arrange**: Set up the conditions for the test.
    - **Act**: Execute the code being tested.
    - **Assert**: Verify the result.

3. **Isolate Tests**: Each test should be independent and not rely on the state left by other tests. Use mocking frameworks like Mockito to isolate dependencies.

4. **Test Edge Cases**: Ensure you cover edge cases and not just the happy path.

5. **Use Assertions Effectively**: Use assertions to check the expected outcomes. Kotlin's `assertEquals`, `assertTrue`, `assertFalse`, etc., are useful.

6. **Keep Tests Small and Focused**: Each test should verify a single behavior or scenario.

7. **Use Setup and Teardown Methods**: Use `@BeforeEach` and `@AfterEach` to set up and clean up resources before and after each test.

8. **Mock External Dependencies**: Use mocking to simulate external dependencies and control their behavior.

9. **Test Private Methods Indirectly**: Test private methods through public methods that call them.

10. **Use Parameterized Tests**: For testing multiple inputs with the same logic, use parameterized tests.

Here is an example of a well-structured unit test in Kotlin using JUnit 5 and Mockito:

```kotlin
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ExampleServiceTests {

    @Mock
    lateinit var dependency: Dependency

    @InjectMocks
    lateinit var exampleService: ExampleService

    @BeforeEach
    fun setUp() {
        // Arrange: Set up common test data or mocks
        `when`(dependency.someMethod()).thenReturn("mocked result")
    }

    @Test
    fun `should return expected result when condition is met`() {
        // Act: Call the method under test
        val result = exampleService.performAction()

        // Assert: Verify the result
        assertEquals("expected result", result)
    }

    @Test
    fun `should throw exception when invalid input is provided`() {
        // Arrange: Set up specific conditions for this test
        `when`(dependency.someMethod()).thenThrow(IllegalArgumentException::class.java)

        // Act & Assert: Verify that the exception is thrown
        assertThrows<IllegalArgumentException> {
            exampleService.performAction()
        }
    }
}
```

This example demonstrates the use of descriptive test names, the Arrange-Act-Assert pattern, mocking dependencies, and verifying outcomes.