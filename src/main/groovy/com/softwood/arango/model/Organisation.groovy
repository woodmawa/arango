package com.softwood.arango.model


import com.arangodb.springframework.annotation.HashIndex
import com.arangodb.springframework.annotation.Relations
import com.softwood.arango.relationships.OperatesFromMany
import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id
import com.arangodb.springframework.annotation.Document

@Document("organisations")
@HashIndex(fields = ["name"], unique = true)
@EqualsAndHashCode
//set unique index
public class Organisation {

    enum OrgType {
        CSP('Communications Service provider', "telco "),
        ConsumerPackagedGoods('Consumer Packaged Goods', "CPG"),
        utilities('Utilities', "Utilities"),
        Finance('Finance', "financial service provider"),
        Other('unspecified', "dont know type")

        final String id
        final String description
        static final Map map
    }

    @Id
    private String id

    String name
    String webAddress
    Integer inaugurated
    OrgType type


    @Relations(edges = OperatesFromMany.class, maxDepth = 1, direction = Relations.Direction.ANY, lazy = true)
    //@Relations (edges=OperatesFromMany, lazy=true)  //have to use lazy true to avoid a recursion
    private List<Site> sites = []

    String toString() {
        "Organisation : $name [$id]"
    }
}
