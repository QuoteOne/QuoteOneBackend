package com
import com.app.repository.models.EntityValues
import com.app.repository.models.ValidationPolicy
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity


@ConfigurationProperties(prefix = "spring.admin")
class DefaultAdminProperties(
    val username: String,
    val email: String,
    val password: String,
    val roles: List<String>
)

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class Application(
) : CommandLineRunner {
    val logger = LoggerFactory.getLogger(Application::class.java)
    override fun run(vararg args: String?) {
        val entityValues = EntityValues(
            id = null,

            values = mutableMapOf(
                "color" to "black"
            ),
            validationPolicies = mutableSetOf(
                ValidationPolicy(
                    id = null,
                    name = "color should be black",
                    attribute = "color",
                    validationType = ValidationType.EQUAL,
                    valueType = ValueType.STRING,
                    value = "black"
                )
            )
        )

        println(entityValues)
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
