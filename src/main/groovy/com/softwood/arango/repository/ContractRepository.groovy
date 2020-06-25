package com.softwood.arango.repository

import com.arangodb.springframework.repository.ArangoRepository
import com.softwood.arango.model.Contract
import com.softwood.arango.model.Organisation

//needs the class type, and type of field tagged with @Id in model
public interface ContractRepository extends ArangoRepository<Contract, String> {

    Optional<Contract> findByName(String name)
}
