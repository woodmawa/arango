package com.softwood.arango.repository.contact

import com.arangodb.ArangoCursor
import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import com.arangodb.springframework.annotation.BindVars
import com.arangodb.springframework.annotation.Query
import com.arangodb.springframework.repository.ArangoRepository
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module
import com.softwood.arango.model.Customer
import com.softwood.arango.model.Site
import com.softwood.arango.model.contact.Contact
import org.springframework.data.repository.query.Param

//needs the class type, and type of field tagged with @Id in model
public interface ContactRepository extends ArangoRepository<Contact, String> {

    List<Contact> findBySurName(String name)
    List<Contact> findByFirstName(String name)

    List<Contact> findByFirstNameIgnoreCase (String name)
    List<Contact> findBySurNameIgnoreCase (String name)

}
