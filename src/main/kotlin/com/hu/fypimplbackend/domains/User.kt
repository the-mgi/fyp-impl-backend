package com.hu.fypimplbackend.domains

import com.kdsp.ds.enums.Gender
import javax.persistence.*
import javax.validation.constraints.Pattern

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var firstName: String? = null,

    var lastName: String? = null,
    var password: String? = null,
    var username: String? = null,

    @Column(unique = true, nullable = false)
    @Pattern(
        regexp = "^([a-z0-9.-]+)@([a-z0-9-]+)\\.([a-z]{2,8})(\\.[a-z]{2,8})?\$",
    )
    var emailAddress: String? = null,

    @Column(unique = true, nullable = false)
    var phoneNumber: String? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "address_id",
        referencedColumnName = "address_id",
    )
    var address: Address? = null,

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null,

    @ManyToMany
    var roles: MutableList<Role> = mutableListOf(),

    var imagePath: String? = null,
    var imageFileName: String? = null

) {
    override fun toString(): String {
        return "User(firstName=$firstName, lastName=$lastName, password=$password, emailAddress=$emailAddress, phoneNumber=$phoneNumber, address=$address, gender=$gender, roles=$roles, username=$username)"
    }
}