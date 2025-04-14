package com.app.config

import com.app.repository.models.EntityValues
import com.app.repository.models.ValidationPolicy
import com.security.repository.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer


@Configuration
class SpringDataRestConfig {

    @Bean
    fun repositoryRestConfigurer(): RepositoryRestConfigurer {
        return RepositoryRestConfigurer.withConfig { config: RepositoryRestConfiguration ->
            config.exposeIdsFor(
                User::class.java,
                EntityValues::class.java,
                ValidationPolicy::class.java
            )
        }
    }
}