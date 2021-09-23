package com.hu.fypimplbackend.domains

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "country")
@JsonIgnoreProperties(ignoreUnknown = true)
class Country(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "country_id")
    var countryId: Long? = null,

    @Column(nullable = false)
    var name: String? = null,

    var iso3: String? = null,
    var iso2: String? = null,
    var phoneCode: String? = null,
    var capital: String? = null,
    var currency: String? = null,
    var native: String? = null,
    var currencySymbol: String? = null,
    var topLevelDomain: String? = null,
    var region: String? = null,
    var subRegion: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var activeStatus: Boolean = true,

    @Embedded
    var timezone: Timezone? = null

) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Country

        if (countryId != other.countryId) return false
        if (name != other.name) return false
        if (phoneCode != other.phoneCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = countryId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + phoneCode.hashCode()
        return result
    }

    override fun toString(): String {
        return "Country(countryId='$countryId', name='$name', iso3='$iso3', iso2='$iso2', phoneCode='$phoneCode', capital='$capital', currenct='$currency', native='$native', currencySymbol='$currencySymbol', topLevelDomain='$topLevelDomain', region='$region', subRegion='$subRegion', timezone=$timezone, latitude='$latitude', longitude='$longitude', activeStatus=$activeStatus)"
    }
}