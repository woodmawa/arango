package com.softwood.arango.repository

import com.arangodb.ArangoCursor
import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import com.arangodb.entity.BaseDocument
import com.arangodb.model.AqlQueryOptions
import com.arangodb.springframework.annotation.Query
import com.arangodb.springframework.repository.ArangoRepository
import com.arangodb.util.MapBuilder
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module
import com.softwood.arango.model.Customer
import com.softwood.arango.model.Organisation
import com.softwood.arango.model.Site
import org.springframework.data.repository.query.Param

//needs the class type, and type of field tagged with @Id in model
public interface CustomerRepository extends ArangoRepository<Customer, String> {

    Customer findByName(String name)

    List<Customer> findByNameIgnoreCase (String name)

    //builds bindparam and applies with query string
    //exact @Query("FOR c IN #collection FILTER c.name == @name RETURN c")

    @Query("FOR c IN #collection FILTER LIKE(c.name, @name, true) RETURN c")
    ArangoCursor<Customer> custQueryByNameIlike (@Param("name") String name)

    @Query ("FOR c IN customers FOR o IN organisations filter c.organisation == o._id RETURN c " )  //merge (c, {organisation: o} )
    List<Customer> customerListWithEmbeddedOrg ()

    /** try and query from org-site relationship **/
    @Query ("""
for v in 1..1 outbound 
    @orgId 
    operatesFromSites
    return distinct v
""")
    List<Site> customerSitesList (@Param ("orgId") String oid)

    //use the java driver direct, register extra pack for LocalDateTime etc
    ArangoDB arango = new ArangoDB.Builder().registerModule(new VPackJdk8Module()).build()
    ArangoDatabase testDb = arango.db("testDB")

    ArangoCursor<BaseDocument> dcursor = testDb.query(
            """FOR c IN customers 
                FOR o IN organisations 
                    FILTER c.organisation == o._id 
                    RETURN merge (c, {organisation: o})""",
            BaseDocument)

    /*ArangoCursor<BaseDocument> dcursor = testDb.query (
            """FOR c IN customers 
                FOR o IN organisations 
                    FILTER c.organisation == o._id 
                    RETURN merge (c, {organisation: o})""",
            new MapBuilder(),
            new AqlQueryOptions(),
            BaseDocument
    )*/

    def dcres =  dcursor?.toList()

    /*
    Collection<Site> getSites()

    void addSite (Site site)
    void removeSite (Site site)
    */


}
