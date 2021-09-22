package com.hu.fypimplbackend.domains

import javax.persistence.Embeddable

@Embeddable
class Timezone(
    var zoneName: String? = null,
    var gmtOffset: Long? = null,
    var gmtOffsetName: String? = null,
    var abbreviation: String? = null,
    var tzName: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Timezone

        if (zoneName != other.zoneName) return false
        if (gmtOffset != other.gmtOffset) return false
        if (gmtOffsetName != other.gmtOffsetName) return false
        if (abbreviation != other.abbreviation) return false
        if (tzName != other.tzName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = zoneName.hashCode()
        result = 31 * result + gmtOffset.hashCode()
        result = 31 * result + gmtOffsetName.hashCode()
        result = 31 * result + abbreviation.hashCode()
        result = 31 * result + tzName.hashCode()
        return result
    }

    override fun toString(): String {
        return "Timezone(zoneName='$zoneName', gmtOffset=$gmtOffset, gmtOffsetName='$gmtOffsetName', abbreviation='$abbreviation', tzName='$tzName')"
    }
}