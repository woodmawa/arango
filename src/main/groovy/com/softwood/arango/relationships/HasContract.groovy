package com.softwood.arango.relationships

import com.arangodb.springframework.annotation.Edge
import com.arangodb.springframework.annotation.From
import com.arangodb.springframework.annotation.To
import com.softwood.arango.model.Contract
import com.softwood.arango.model.Organisation
import com.softwood.arango.model.Site
import org.springframework.data.annotation.Id

import java.time.LocalDateTime


@Edge
class HasContract {

    @Id
    private String id

    @From(lazy = true)
    Organisation owningOrg

    @To
    Contract contract

    @Override
    String toString() {
        return "contract [id=$contract] belonging to org [id=$owningOrg]"
    }
}