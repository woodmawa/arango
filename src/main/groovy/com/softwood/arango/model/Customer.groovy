package com.softwood.arango.model

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import com.arangodb.springframework.annotation.Relations
import com.softwood.arango.relationships.HasContract
import com.softwood.arango.relationships.OperatesFromSites
import com.softwood.arango.repository.OperatesFromSitesRelationshipRepository
import com.softwood.arango.repository.OrganisationRepository
import com.softwood.arango.repository.SiteRepository
import groovy.transform.EqualsAndHashCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id

import java.time.LocalDateTime

@Document("customers")
@HashIndex(fields = ["name"], unique = true)
@EqualsAndHashCode
class Customer extends PartyRole {

    @Id
    private String id

    //get read only copy of id
    final String getId (){
        id
    }

    String name
    String shortName
    String description
    String webAddress
    String status = "Active"
    String statusReason

    LocalDateTime dateSigned
    final LocalDateTime createdDate = LocalDateTime.now()

    @Relations(edges = HasContract.class, maxDepth = 1, direction = Relations.Direction.ANY, lazy = true)
    Collection<Contract> contracts = []


}
