package com.softwood.arango.model

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import com.arangodb.springframework.annotation.Ref
import com.arangodb.springframework.annotation.To
import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id

@Document("sites")
@HashIndex(fields = ["name"], unique = false)
@EqualsAndHashCode
class Site {

    @Id
    private String id

    @Ref
    Organisation org

    String name

    String toString() {
        "Site : $name [$id]"
    }
}
