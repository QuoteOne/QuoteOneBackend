package com.app.config

import com.app.repository.common.*
import com.app.repository.identity.company.ArchitectureFirm
import com.app.repository.identity.company.BaseCompany
import com.app.repository.identity.company.MaterialSupplier
import com.app.repository.identity.individual.DesignerProfile
import com.app.service.mapper.MaterialMapper
import com.security.repository.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.http.HttpMethod


@Configuration
class SpringDataRestConfig {

    @Bean
    fun repositoryRestConfigurer(): RepositoryRestConfigurer {
        return RepositoryRestConfigurer.withConfig { config: RepositoryRestConfiguration ->
            config.exposeIdsFor(
                Product::class.java,
                ProductCategory::class.java,
                ProductAssociation::class.java,
                ProductCategoryAssociation::class.java,
                User::class.java,
                EntityValues::class.java,
                ValidationPolicy::class.java,

                BaseCompany::class.java,
                ArchitectureFirm::class.java,
                MaterialSupplier::class.java,
                DesignerProfile::class.java
            ).exposureConfiguration.forDomainType(Product::class.java)
                .withItemExposure { metadata, httpMethods -> httpMethods.disable(HttpMethod.POST) }
                .withCollectionExposure { metadata, httpMethods -> httpMethods.disable(HttpMethod.POST) }

        }
    }
}