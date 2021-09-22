package com.hu.fypimplbackend.domains

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "city")
@JsonIgnoreProperties(ignoreUnknown = true)
class City(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "city_id")
    val cityId: Long? = null,

    @Column(nullable = false)
    val cityName: String? = null,

    @Column(nullable = false, unique = true)
    var cityCode: String? = null,

    @OneToOne
    @JoinColumn(
        name = "country_id",
        referencedColumnName = "country_id",
        nullable = false
    )
    var country: Country? = null,

    @OneToOne
    @JoinColumn(
        name = "state_id",
        referencedColumnName = "state_id"
    )
    var state: State? = null

) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as City

        if (cityId != other.cityId) return false

        return true
    }

    override fun hashCode(): Int {
        return cityId?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "City(cityId=$cityId, cityName=$cityName, cityCode=$cityCode, country=$country, state=$state)"
    }
}