package com.softwood.arango.repository

import com.arangodb.springframework.repository.ArangoRepository
import com.softwood.arango.model.Customer
import com.softwood.arango.model.Dummy

//needs the class type, and type of field tagged with @Id in model
public interface DummyRepository extends ArangoRepository<Dummy, String> {

    Customer findByName(String name)

}
