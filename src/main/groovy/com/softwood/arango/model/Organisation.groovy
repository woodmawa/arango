package com.softwood.arango.model

import com.arangodb.springframework.annotation.Edge
import com.arangodb.springframework.annotation.From
import com.arangodb.springframework.annotation.Relations
import com.arangodb.springframework.annotation.To
import com.softwood.arango.relationships.Owns
import org.springframework.data.annotation.Id
import com.arangodb.springframework.annotation.Document

@Document("organisations")
public class Organisation {

    @Id
    private String id

    private String name
    private String webAddress
    private Integer inaugurated

    //@From
    //private List<Owns> owns

    @Relations (edges = Owns.class, maxDepth=1, direction= Relations.Direction.ANY)
    private List<Site> sites = []

    String toString () {
        "Organisation : $name [$id]"
    }
}
