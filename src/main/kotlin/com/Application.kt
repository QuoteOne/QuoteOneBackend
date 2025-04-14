package com
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

    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
