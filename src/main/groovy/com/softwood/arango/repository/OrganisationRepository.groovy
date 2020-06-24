package com.softwood.arango.repository


import com.softwood.arango.model.Organisation
import com.arangodb.springframework.repository.ArangoRepository

//needs the class type, and type of field tagged with @Id in model
public interface OrganisationRepository extends ArangoRepository<Organisation, String> {

    Collection<Organisation> findByName(String name)
}
