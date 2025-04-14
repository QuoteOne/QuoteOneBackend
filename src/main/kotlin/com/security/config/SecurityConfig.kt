package com.security.config

import com.security.service.AuthService
import com.security.web.filter.JwtAuthenticationFilter
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.password.CompromisedPasswordChecker
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker
import org.springframework.security.web.firewall.HttpFirewall
import org.springframework.security.web.firewall.StrictHttpFirewall
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class SpringSecurityConfig(
    val webProperties: WebProperties,
    @Lazy val authService: AuthService,
) {

    private val logger = LoggerFactory.getLogger(SpringSecurityConfig::class.java)

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
    ): SecurityFilterChain {
        http
            .sessionManagement { sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .cors { cors -> cors.configurationSource(withDefaultCorsConfigurationSource()) }
            .authorizeHttpRequests(withDefaultChain())
            .csrf { it.disable() }
        return http.build()
    }

    @Bean
    fun httpFirewall(): HttpFirewall {
        val defaultHttpFirewall = StrictHttpFirewall()
        defaultHttpFirewall.setAllowUrlEncodedSlash(true)
        defaultHttpFirewall.setAllowBackSlash((true))
        defaultHttpFirewall.setAllowSemicolon(true)
        return defaultHttpFirewall
    }


    fun withDefaultChain(): Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {
        return Customizer { auth ->
            auth
                .requestMatchers(*webProperties.unprotectedRoutes.toTypedArray()).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasRole("USER")
                .requestMatchers("/api/private/**").hasRole("GUEST")
                .anyRequest().authenticated()
        }
    }

    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(authService, webProperties.unprotectedRoutes)
    }

    fun withDefaultCorsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowedOriginPatterns = listOf("*")
        config.allowedMethods = listOf("*")
        config.allowCredentials = true;
        config.allowedHeaders = listOf("*")
        config.maxAge = 3600L;
        val source = UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    fun defaultPasswordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun defaultCompromisedPasswordChecker(): CompromisedPasswordChecker {
        return HaveIBeenPwnedRestApiPasswordChecker()
    }

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val chainOfRoles = "ADMIN > USER > GUEST"
        val hierarchy = RoleHierarchyImpl.withDefaultRolePrefix()
        val roleAsList = chainOfRoles.split(">")
        for (idx in roleAsList.indices) {
            if (idx == 0) continue
            val previousRole = roleAsList[idx - 1].trim()
            val currentRole = roleAsList[idx].trim()
            hierarchy.role(previousRole).implies(currentRole)
        }

        return hierarchy.build()
    }

    // and, if using pre-post method security also add
    @Bean
    fun methodSecurityExpressionHandler(roleHierarchy: RoleHierarchy): MethodSecurityExpressionHandler {
        val expressionHandler = DefaultMethodSecurityExpressionHandler()
        expressionHandler.setRoleHierarchy(roleHierarchy)
        return expressionHandler
    }


}