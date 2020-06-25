package com.softwood.arango.model

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import com.arangodb.springframework.annotation.Relations
import com.softwood.arango.relationships.HasContract
import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id

import java.time.LocalDateTime

@Document("customers")
@HashIndex(fields = ["name"], unique = true)
@EqualsAndHashCode
class Customer extends PartyRole {

    @Id
    private String id

    String name
    String description
    String webAddress

    LocalDateTime dateSigned
    LocalDateTime createdDate

    @Relations(edges = HasContract.class, maxDepth = 1, direction = Relations.Direction.ANY, lazy = true)
    Collection<Contract> contracts = []


}
