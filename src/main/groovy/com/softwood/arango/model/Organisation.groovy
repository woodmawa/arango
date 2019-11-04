package com.softwood.arango.model


import com.arangodb.springframework.annotation.HashIndex
import com.arangodb.springframework.annotation.Relations
import com.softwood.arango.relationships.OperatesFromMany
import org.springframework.data.annotation.Id
import com.arangodb.springframework.annotation.Document

@Document("organisations")
@HashIndex(fields=["name"], unique=true) //set unique index
public class Organisation {

    enum OrgType {
        CSP ('Communications Service provider', "telco "),
        Bank ('bank', "financial service prover"),
        Other ('unspecified', "dont know type")

        final String id
        final String description
        static final Map map
    }

    @Id
    private String id

    private String name
    private String webAddress
    private Integer inaugurated
    private OrgType type


    @Relations (edges = OperatesFromMany.class, maxDepth=1, direction= Relations.Direction.ANY, lazy=true)
    //@Relations (edges=OperatesFromMany, lazy=true)  //have to use lazy true to avoid a recursion
    private List<Site> sites = []

    String toString () {
        "Organisation : $name [$id]"
    }
}
