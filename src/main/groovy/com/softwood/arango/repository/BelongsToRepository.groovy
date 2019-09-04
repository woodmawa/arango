package com.softwood.arango.repository

import com.arangodb.springframework.repository.ArangoRepository
import com.softwood.arango.relationships.WorksInMany

//needs the class type, and type of field tagged with @Id in model
//note can extend without the second type arg
public interface BelongsToRepository extends ArangoRepository<WorksInMany, String> {}
