package com.softwood.arango.model.product

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id

@Document("products")
@HashIndex(fields = ["name"], unique = true)
@EqualsAndHashCode
class Product {

    @Id
    private String id

    String name

    String toString () {
        "contract :  $name [$id]"
    }
}
