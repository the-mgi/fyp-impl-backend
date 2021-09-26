package com.hu.fypimplbackend.domains

import com.fasterxml.jackson.annotation.*
import javax.persistence.*

@Entity
@Table(name = "state")
@JsonIgnoreProperties(ignoreUnknown = true)
class State(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "state_id")
    var stateId: Long? = null,

    @Column(nullable = false)
    var statName: String? = null,

    @Column(nullable = false)
    var stateCode: String? = null,

    @OneToOne
    @JoinColumn(
        name = "country_id",
        referencedColumnName = "country_id"
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "countryId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonBackReference
    var country: Country? = null,

    var iso2: String? = null,
    var activeStatus: String? = null,

    @Column(nullable = false)
    var latitude: Double? = null,

    @Column(nullable = false)
    var longitude: Double? = null,

) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

        if (stateId != other.stateId) return false

        return true
    }

    override fun hashCode(): Int {
        return stateId?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "State(stateId=$stateId, statName=$statName, stateCode=$stateCode, country=$country, iso2=$iso2, activeStatus=$activeStatus, latitude=$latitude, longitude=$longitude)"
    }
}