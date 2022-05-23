package com.hu.fypimplbackend.repositories

import com.hu.fypimplbackend.domains.Friends
import com.hu.fypimplbackend.enums.RequestStatusType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FriendsRepository : JpaRepository<Friends, Long> {
    fun findByFromUsernameAndToUsername(from: String, to: String): Optional<Friends>
    fun findAllByFromUsernameAndFriendShipStatus(fromUsername: String, requestStatusType: RequestStatusType): List<Friends>
    fun findAllByToUsernameAndFriendShipStatus(fromUsername: String, requestStatusType: RequestStatusType): List<Friends>
}