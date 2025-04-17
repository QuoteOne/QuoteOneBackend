package com.app.config

import com.app.repository.common.*
import com.app.repository.identity.company.ArchitectureFirm
import com.app.repository.identity.company.BaseCompany
import com.app.repository.identity.company.MaterialVendor
import com.app.repository.identity.individual.DesignerProfile
import com.app.repository.material.repository.models.Board
import com.app.repository.material.repository.models.DoorPanel
import com.app.repository.material.repository.models.Drawer
import com.app.repository.material.repository.models.Processing
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
                Product::class.java,
                ProductCategory::class.java,
                ProductAssociation::class.java,
                ProductCategoryAssociation::class.java,
                User::class.java,
                EntityValues::class.java,
                ValidationPolicy::class.java,

                BaseCompany::class.java,
                ArchitectureFirm::class.java,
                MaterialVendor::class.java,
                DesignerProfile::class.java,

                Board::class.java,
                Drawer::class.java,
                Processing::class.java,
                DoorPanel::class.java
            )
        }
    }
}