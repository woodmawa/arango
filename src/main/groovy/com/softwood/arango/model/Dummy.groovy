package com.softwood.arango.model

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id

@Document("Dummies")
@HashIndex(fields = ["name"], unique = true)
@EqualsAndHashCode
class Dummy {
    @Id
    private String id

    //get read only copy of id
    final String getId (){
        id
    }

    String name

}
