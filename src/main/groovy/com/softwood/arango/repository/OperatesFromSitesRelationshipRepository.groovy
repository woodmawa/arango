package com.softwood.arango.repository

import com.arangodb.springframework.repository.ArangoRepository
import com.softwood.arango.relationships.OperatesFromSites

//needs the class type, and type of field tagged with @Id in model
//note can extend without the second type arg
public interface OperatesFromSitesRelationshipRepository extends ArangoRepository<OperatesFromSites, String> {}
