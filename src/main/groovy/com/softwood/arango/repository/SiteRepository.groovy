package com.softwood.arango.repository

import com.arangodb.springframework.repository.ArangoRepository
import com.softwood.arango.model.Organisation
import com.softwood.arango.model.Site

//needs the class type, and type of field tagged with @Id in model
public interface SiteRepository extends ArangoRepository<Site, String> {

    Collection<Site> findByName (String name)
}
