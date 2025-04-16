package com.app.repository.common

import com.app.service.valdator.IValidationRequired
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*


@Entity
@Table(name = "entity_values")
class EntityValues(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    val id: UUID? = null,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "entity_values_validation_policy",
        joinColumns = [JoinColumn(name = "entity_id")],
        inverseJoinColumns = [JoinColumn(name = "validation_policy_id")]
    )
    override val validationPolicies: MutableSet<ValidationPolicy> = mutableSetOf(),


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "values", columnDefinition = "json")
    override var values: MutableMap<String, Any> = mutableMapOf()

): IValidationRequired {
    init {
        validate()
    }

    fun setValue(attribute: String, value: Any) {
        values[attribute] = value
        validate()
    }

    companion object {
        fun empty(): EntityValues {
            return EntityValues()
        }
    }
}