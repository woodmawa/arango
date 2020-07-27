package com.softwood.arango.model.contact

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id

import java.time.Duration

@Document("contacts")
@EqualsAndHashCode
class ContactMedium {

    @Id
    private String id

    //get read only copy of id
    final String getId (){
        id
    }

    boolean preferred
    String type
    Duration validFor

    //optional 1 to 1
    MediumCharacteristic characteristic
}

