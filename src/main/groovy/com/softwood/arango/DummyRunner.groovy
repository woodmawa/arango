package com.softwood.arango

import com.arangodb.entity.CollectionPropertiesEntity
import com.arangodb.springframework.core.ArangoOperations
import com.arangodb.springframework.core.CollectionOperations
import com.softwood.arango.model.Dummy
import com.softwood.arango.model.Organisation
import com.softwood.arango.model.Site
import com.softwood.arango.relationships.OperatesFromSites
import com.softwood.arango.repository.DummyRepository
import com.softwood.arango.repository.OperatesFromSitesRelationshipRepository
import com.softwood.arango.repository.OrganisationRepository
import com.softwood.arango.repository.SiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.ComponentScan
import org.springframework.beans.factory.annotation.Autowired


@ComponentScan("com.softwood.arango")
public class DummyRunner implements CommandLineRunner {

    @Autowired
    private ArangoOperations operations

    static Collection<Organisation> createOrgs() {

        println "\t> createDummies: created 3 Dummies "
        Arrays.asList(
                new Dummy (name: "Vodafone"),
                new Dummy(name: "BT"),
                new Dummy(name: "HSBC")
        )
    }


    @Autowired
    private DummyRepository dummyRepo


    @Override
    public void run(final String... args) throws Exception {

        println "--- running Dummy   runner ---"

        // first drop the database so that we can run this multiple times with the same dataset
        operations.dropDatabase()


        // save a single entity in the database
        // there is no need of creating the collection first. This happen automatically
        final Dummy dummy = new Dummy (name: "Super Dummy")
        dummyRepo.save(dummy)

        CollectionOperations dummyColl = operations.collection(Dummy)

        CollectionPropertiesEntity props = dummyColl.getProperties()
        println "collection ('Dummy') with name : " + props.name

        // the generated id from the database is set in the original entity
        println(String.format("bank '%s' dummy saved in the database with id: '%s'", dummy.name, dummy.id))


        Optional<Dummy> fndDummy = dummyRepo.findOne()
        println(String.format("Found org with name : %s and id %s", fndDummy.get().name, fndDummy.get().id))


        //println "create and save 3 orgs "
        //dummyRepo.saveAll(DummyRunner.createOrgs())

        assert dummyRepo.count() == (1)

        //Optional<Organisation> res2 = orgRepo.findByName("BT")
        //assert res2.isPresent()


    }

}

