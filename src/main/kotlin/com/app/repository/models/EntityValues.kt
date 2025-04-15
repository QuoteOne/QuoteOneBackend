package com.app.repository.models

import com.app.service.valdator.IValidationRequired
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.*
import java.util.*


@Converter
class HashMapConverter : AttributeConverter<MutableMap<String, Any>, String> {

    private val mapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: MutableMap<String, Any>?): String {
        return attribute?.let { mapper.writeValueAsString(it) } ?: "{}"
    }

    override fun convertToEntityAttribute(rawString: String?): MutableMap<String, Any> {
        return rawString?.let { mapper.readValue<MutableMap<String, Any>>(it) } ?: mutableMapOf()
    }
}



@Entity
@Table(name = "entity_values")
class EntityValues(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    val id: UUID?,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "entity_values_validation_policy",
        joinColumns = [JoinColumn(name = "entity_id")],
        inverseJoinColumns = [JoinColumn(name = "validation_policy_id")]
    )
    override val validationPolicies: MutableSet<ValidationPolicy> = mutableSetOf(),


    @Convert(converter = HashMapConverter::class)
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
}