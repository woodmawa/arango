package com.softwood.arango.repository.contact

import com.arangodb.springframework.repository.ArangoRepository
import com.softwood.arango.relationships.HasContract
import com.softwood.arango.relationships.contact.UsesContactMedium

//needs the class type, and type of field tagged with @Id in model
//note can extend without the second type arg
public interface UseContactMediumRelationshipRepository extends ArangoRepository<UsesContactMedium, String> {}
