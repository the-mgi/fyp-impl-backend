package com.hu.fypimplbackend.domains

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
open class BaseEntity {
    @JsonIgnore
    @Column(name = "created_timestamp")
    var createdTimestamp: Timestamp? = null

    @JsonIgnore
    @Column(name = "updated_timestamp")
    var updateTimestamp: Timestamp? = null

    @PrePersist
    fun onCreateObject() {
        this.createdTimestamp = Timestamp(System.currentTimeMillis())
    }

    @PreUpdate
    fun onUpdateObject() {
        this.updateTimestamp = Timestamp(System.currentTimeMillis())
    }

    override fun toString(): String {
        return "BaseEntity(createdTimestamp=$createdTimestamp, updateTimestamp=$updateTimestamp)"
    }
}