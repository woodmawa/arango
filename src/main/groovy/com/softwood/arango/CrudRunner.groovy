package com.softwood.arango


import com.arangodb.entity.CollectionPropertiesEntity
import com.arangodb.springframework.core.CollectionOperations
import com.softwood.arango.relationships.Owns
import com.softwood.arango.model.Site
import com.softwood.arango.repository.OrganisationRepository
import com.softwood.arango.repository.OwnsRepository
import com.softwood.arango.repository.SiteRepository
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
    private OrganisationRepository orgRepo

    @Autowired
    private SiteRepository siteRepo

    @Autowired
    private OwnsRepository ownsRepo

    @Override
    public void run(final String... args) throws Exception {
        // first drop the database so that we can run this multiple times with the same dataset
        operations.dropDatabase()

        // save a single entity in the database
        // there is no need of creating the collection first. This happen automatically
        final Organisation vf = new Organisation(name:"Vodafone", inaugurated: 2000, webAddress:"vodafone.com")
        orgRepo.save(vf)

        final Site s = new Site (name:"Newbury HQ", org: vf)
        siteRepo.save (s)

        assert siteRepo.count() == 1

        Owns owns = new Owns (owningOrg:vf, site:s)  //create relationship
        ownsRepo.save (owns)
        println "saved owning site relationship as edge " + owns.dump()

        //vf.sites.add(s) //add site to vf and save
        //orgRepo.save(vf)  //- crashes infinite loop

        CollectionOperations orgColl= operations.collection(Organisation)
        CollectionOperations siteColl= operations.collection(Site)

        CollectionPropertiesEntity props = siteColl.getProperties()
        println "collection ('organisation') with name : " + props.name

        props = siteColl.getProperties()
        println "collection ('site') with name : " + props.name

        // the generated id from the database is set in the original entity
        println(String.format("vf organisation saved in the database with id: '%s'", vf.id))

        // create an example from saved object and use to query the db - findOne returns Optional<T>
        Optional<Organisation> res = orgRepo.findOne(Example.of(vf))
        assert res.isPresent()

        final Organisation foundOrg = res.get()

        orgRepo.findOne()
        println(String.format("Found vertex with name : %s and id %s", foundOrg.name, foundOrg.id))

        /* bulk insert test
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
        println vArr.size() + " verticies to insert in arangodb"

        orgRepo.saveAll (vArr)

        def end = System.nanoTime()
        def duration = (end - start)
        def period = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS)/1000

        println "Arango 100K records done in duration " + period + " seconds"

        */

    }

}

