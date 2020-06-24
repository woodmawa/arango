package com.softwood.arango.model

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id

import java.time.Duration
import java.time.LocalDateTime

@Document("contracts")
@HashIndex(fields = ["name"], unique = true)
@EqualsAndHashCode
class Contract {

    @Id
    private String id

    String name
    LocalDateTime dateSigned
    String documentNumber
    String statementOfIntent
    Duration validFor

    String toString () {
        "contract :  $name [$id]"
    }
}
