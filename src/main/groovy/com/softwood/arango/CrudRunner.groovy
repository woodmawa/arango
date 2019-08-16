package com.softwood.arango

import com.arangodb.ArangoCollection
import com.arangodb.entity.CollectionPropertiesEntity
import com.arangodb.springframework.core.CollectionOperations
import com.softwood.arango.repository.OrganisationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.ComponentScan

import com.softwood.arango.model.Organisation
import com.arangodb.springframework.core.ArangoOperations
import org.springframework.data.domain.Example

import java.util.concurrent.TimeUnit

@ComponentScan("com.softwood.arango")
public class CrudRunner implements CommandLineRunner {

    @Autowired
    private ArangoOperations operations
    @Autowired
    private OrganisationRepository repository

    @Override
    public void run(final String... args) throws Exception {
        // first drop the database so that we can run this multiple times with the same dataset
        operations.dropDatabase()

        // save a single entity in the database
        // there is no need of creating the collection first. This happen automatically
        final Organisation vf = new Organisation(name:"Vodafone", inaugurated: 2000, webAddress:"vodafone.com")
        repository.save(vf)

        CollectionOperations coll= operations.collection(Organisation)

        CollectionPropertiesEntity props = coll.getProperties()
        println props.name

        // the generated id from the database is set in the original entity
        println(String.format("vf organisation saved in the database with id: '%s'", vf.id))

        // create an example from saved object and use to query the db - findOne returns Optional<T>
        Optional<Organisation> res = repository.findOne(Example.of(vf))
        assert res.isPresent()

        final Organisation foundOrg = res.get()

        repository.findOne()
        println(String.format("Found %s", foundOrg.name))

        def v
        String ref
        def org
        println "insert bulk records"
        def start = System.nanoTime()
        def vArr = []
        //write 100k records
        for (int i=1; i<100001;i++) {
            ref = "object#[${-> i}]"
            org = new Organisation (name: "Object#[$ref]", inaugurated:2000)
             vArr << org

        }
        println vArr.size() + " verticies to insert"

        repository.saveAll (vArr)

        def end = System.nanoTime()
        def duration = (end - start)
        def period = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS)/1000

        println "Arango 100K records done in duration " + period + " seconds"

    }

}

