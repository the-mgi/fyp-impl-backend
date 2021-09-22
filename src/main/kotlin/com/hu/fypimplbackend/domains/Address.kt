package com.hu.fypimplbackend.domains

import javax.persistence.*

@Entity
@Table(name = "address")
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    var addressId: Long? = null,

    @OneToOne
    @JoinColumn(
        name = "city_id",
        referencedColumnName = "city_id",
        nullable = false
    )
    var city: City? = null,

    @Column(nullable = false)
    var addressLineOne: String? = null,

    var addressLineTwo: String? = null,

) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (addressId != other.addressId) return false

        return true
    }

    override fun hashCode(): Int {
        return addressId?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Address(addressId=$addressId, city=$city, addressLineOne=$addressLineOne, addressLineTwo=$addressLineTwo)"
    }
}