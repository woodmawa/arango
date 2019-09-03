package com.softwood.arango.model

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.Ref
import com.arangodb.springframework.annotation.To
import org.springframework.data.annotation.Id

@Document("sites")
class Site {

    @Id
    private String id

    @Ref
    private Organisation org

    private String name

    String toString () {
        "Site : $name [$id]"
    }
}
