package com.softwood.arango.relationships

import com.arangodb.springframework.annotation.Edge
import com.arangodb.springframework.annotation.From
import com.arangodb.springframework.annotation.To
import com.softwood.arango.model.Organisation
import com.softwood.arango.model.Site
import org.springframework.data.annotation.Id


@Edge ("owns")
class Owns {

    @Id
    private String id

    @From(lazy=true)
    private Organisation owningOrg

    @To
    private Site site

    /*@From
    private Organisation org
    @To
    private Site site*/
}