package com.app.repository.identity.individual

import com.security.repository.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "designer_profiles")
data class DesignerProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: User,

    @Column(nullable = false)
    val contactPerson: String,

    @Column(nullable = false)
    val phoneNumber: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = true)
    val address: String?,

    @Column(nullable = true)
    val website: String?,

    @Column(nullable = true)
    val businessLicenseNumber: String?,

    @Column(nullable = false)
    val createdAt: Date = Date(),

    @Column(nullable = false)
    val updatedAt: Date = Date()
)
