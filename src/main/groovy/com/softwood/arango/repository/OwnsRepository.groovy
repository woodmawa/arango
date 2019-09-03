package com.softwood.arango.repository

import com.arangodb.springframework.repository.ArangoRepository
import com.softwood.arango.relationships.Owns
import com.softwood.arango.model.Site

//needs the class type, and type of field tagged with @Id in model
public interface OwnsRepository extends ArangoRepository<Owns, String> {}
