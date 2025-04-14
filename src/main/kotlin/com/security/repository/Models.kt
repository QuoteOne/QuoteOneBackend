package com.security.repository

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @Column(nullable = false, unique = true)
    private val username: String,

    @Column(nullable = true)
    @JsonIgnore
    private val password: String?,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    val roles: MutableSet<String>,

    @Column(nullable = false)
    val provider: String,

    @Column(nullable = true)
    val externalId: String?,
) : UserDetails {

    companion object {
        const val DefaultPlatform = "LOCAL"
    }

    constructor(name: String, password: String, roles: Iterable<String>, provider: String, externalId: String?) : this(
        id = null,
        username = name,
        password = password,
        roles = roles.toMutableSet(),
        provider = provider,
        externalId = externalId,
    )

    @JsonIgnore
    override fun getAuthorities(): Collection<GrantedAuthority> =
        roles.map { GrantedAuthority { it } }

    override fun getPassword(): String = password ?: ""

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}