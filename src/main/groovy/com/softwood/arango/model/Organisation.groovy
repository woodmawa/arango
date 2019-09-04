package com.softwood.arango.model


import com.arangodb.springframework.annotation.HashIndex
import com.arangodb.springframework.annotation.Relations
import com.softwood.arango.relationships.OperatesFromMany
import org.springframework.data.annotation.Id
import com.arangodb.springframework.annotation.Document

@Document("organisations")
@HashIndex(fields=["name"], unique=true) //set unique index
public class Organisation {

    @Id
    private String id

    private String name
    private String webAddress
    private Integer inaugurated


    @Relations (edges = OperatesFromMany.class, maxDepth=1, direction= Relations.Direction.ANY, lazy=false)
    private List<Site> sites = []

    String toString () {
        "Organisation : $name [$id]"
    }
}
