package com.hu.fypimplbackend.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.hu.fypimplbackend.enums.Gender
import javax.persistence.*
import javax.validation.constraints.Pattern

@Entity
@Table(name = "sys_user")
@JsonInclude(JsonInclude.Include.ALWAYS)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    var id: Long? = null,

    @Column(nullable = false)
    var firstName: String? = null,

    var lastName: String? = null,

    @Pattern(
        regexp = """^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,20}$"""
    )
    var password: String? = null,

    @Column(unique = true, nullable = false)
    var username: String? = null,

    @Column(unique = true, nullable = false)
    @Pattern(
        regexp = """^([a-z0-9.-]+)@([a-z0-9-]+)\\.([a-z]{2,8})(\\.[a-z]{2,8})?\$""",
    )
    var emailAddress: String? = null,

    @Column(unique = true)
    var phoneNumber: String? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "address_id",
        referencedColumnName = "address_id",
    )
    var address: Address? = null,

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    var roles: MutableList<Role> = mutableListOf(),

    var imagePath: String? = null,
    var profileStatus: String? = null,
    var imageFileName: String? = null,
    var otpCode: String? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    var interests: MutableList<Interest> = mutableListOf(),

) : BaseEntity() {
    override fun toString(): String {
        return "User(" +
                "id=$id," +
                " firstName=$firstName," +
                " lastName=$lastName," +
                " password=$password," +
                " username=$username," +
                " emailAddress=$emailAddress," +
                " phoneNumber=$phoneNumber," +
                " address=$address," +
                " gender=$gender," +
                " roles=$roles," +
                " imagePath=$imagePath," +
                " imageFileName=$imageFileName," +
                " profileStatus=$profileStatus," +
                " otpCode=$otpCode," +
                " interests=$interests)"
    }
}