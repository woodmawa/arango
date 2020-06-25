package com.softwood.arango.repository

import com.arangodb.springframework.repository.ArangoRepository
import com.softwood.arango.model.Customer
import com.softwood.arango.model.Organisation
import com.softwood.arango.model.Site

//needs the class type, and type of field tagged with @Id in model
public interface CustomerRepository extends ArangoRepository<Customer, String> {

    Customer findByName(String name)

    /*
    Collection<Site> getSites()

    void addSite (Site site)
    void removeSite (Site site)
    */


}
