package com.hu.fypimplbackend.domains

import com.hu.fypimplbackend.enums.RequestStatusType
import javax.persistence.*

@Entity
@Table(name = "friends")
class Friends(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "friends_id")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    var friendShipStatus: RequestStatusType,

    var fromUsername: String? = null,
    var toUsername: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Friends

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Friends(id=$id, requestStatusToMany=$friendShipStatus, from=$fromUsername, to=$toUsername)"
    }
}