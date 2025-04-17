package com
import com.app.repository.common.IProductRepository
import com.app.repository.common.Product
import com.app.repository.material.repository.IBoardRepository
import com.app.repository.material.repository.models.Board
import com.app.service.ProductService
import com.fasterxml.jackson.databind.ObjectMapper
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
    val productRepository: IBoardRepository,
    val productService: ProductService,
    val mapper: ObjectMapper
) : CommandLineRunner {
    val logger = LoggerFactory.getLogger(Application::class.java)
    override fun run(vararg args: String?) {
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
