package com.softwood.arango.model


import org.springframework.data.annotation.Id
import com.arangodb.springframework.annotation.Document

@Document("organisations")
public class Organisation {

    @Id
    private String id

    private String name
    private String webAddress
    private Integer inaugurated

}