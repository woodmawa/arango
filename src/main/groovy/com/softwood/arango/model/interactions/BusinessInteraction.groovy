package com.softwood.arango.model.interactions

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id

import java.time.LocalDateTime

@Document("contracts")
@HashIndex(fields = ["name"], unique = true)
@EqualsAndHashCode
class BusinessInteraction {
    @Id
    private String id

    LocalDateTime startTime
    LocalDateTime endTime
    String status
    String description

    /** move into relations */
    Collection<BusinessInteraction> relatedInteractions
}
