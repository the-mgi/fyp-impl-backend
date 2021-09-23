package com.hu.fypimplbackend.repositories.address

import com.hu.fypimplbackend.domains.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : JpaRepository<Address, Long>