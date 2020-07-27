package com.softwood.arango.relationships.contact

import com.arangodb.springframework.annotation.Edge
import com.arangodb.springframework.annotation.From
import com.arangodb.springframework.annotation.To
import com.softwood.arango.model.Contract
import com.softwood.arango.model.Customer
import com.softwood.arango.model.contact.Contact
import com.softwood.arango.model.contact.ContactMedium
import org.springframework.data.annotation.Id

@Edge
class UsesContactMedium {

    @Id
    private String id

    @From(lazy = true)
    Contact owningContact

    @To
    ContactMedium medium

    @Override
    String toString() {
        return "contactmedium [id=$medium] belonging to org [id=$owningContact]"
    }
}