package com.hu.fypimplbackend.domains

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
open class BaseEntity {
    @CreationTimestamp
    @Column(
        name = "created_timestamp",
        nullable = false,
        insertable = false,
        updatable = false
    )
    var createdTimestamp: Timestamp? = null

    @UpdateTimestamp
    @Column(
        name = "updated_timestamp",
        nullable = false,
        insertable = false,
        updatable = false
    )
    var updateTimestamp: Timestamp? = null

    override fun toString(): String {
        return "BaseEntity(createdTimestamp=$createdTimestamp, updateTimestamp=$updateTimestamp)"
    }
}