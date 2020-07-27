package com.softwood.arango.model.contact

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import com.arangodb.springframework.annotation.Relations
import com.softwood.arango.model.Contract
import com.softwood.arango.model.PartyRole
import com.softwood.arango.relationships.HasContract
import com.softwood.arango.relationships.contact.UsesContactMedium
import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id

import java.time.LocalDateTime

@Document("contacts")
@HashIndex(fields = ["name"], unique = false)
@EqualsAndHashCode
class Contact {
    @Id
    private String id

    //get read only copy of id
    final String getId (){
        id
    }

    LocalDateTime createdDate
    PartyRole.RoleType contactRole

    String firstName
    String surName

    final String getFullName () {
        firstName ?: ''  + surName ?: ''
    }

    //expect to see 0..1 to many media
    @Relations(edges = UsesContactMedium.class, maxDepth = 1, direction = Relations.Direction.ANY, lazy = true)
    Collection<Contract> contactMedia = []
}
