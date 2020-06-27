package com.softwood.arango


import com.arangodb.entity.CollectionPropertiesEntity
import com.arangodb.springframework.core.CollectionOperations
import com.softwood.arango.model.Site
import com.softwood.arango.relationships.OperatesFromSites
import com.softwood.arango.repository.OperatesFromSitesRelationshipRepository
import com.softwood.arango.repository.OrganisationRepository
import com.softwood.arango.repository.SiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.ComponentScan

import com.softwood.arango.model.Organisation
import com.arangodb.springframework.core.ArangoOperations

@ComponentScan("com.softwood.arango")
public class CrudRunner implements CommandLineRunner {

    @Autowired
    private ArangoOperations operations
    @Autowired
    private OrganisationRepository orgRepo

    @Autowired
    private SiteRepository siteRepo

    @Autowired
    private OperatesFromSitesRelationshipRepository ownsRepo  //edge relationship

    static Collection<Organisation> createOrgs() {

        Arrays.asList(
                new Organisation(name: "Vodafone", inaugurated: 1975, webAddress: "vodafone.com"),
                new Organisation(name: "BT", inaugurated: 1900, webAddress: "BT.com"),
                new Organisation(name: "HSBC", inaugurated: 1950, webAddress: "HSBC.com")
        )
    }

    static Collection<Site> createSites() {
        Arrays.asList(
                new Site(name: "Newbury, HQ"),
                new Site(name: "BT centre, St Pauls"),
                new Site(name: "Canary wharf, HQ"),
                new Site(name: "Ipswich Branch, HQ")
        )

    }


    @Override
    public void run(final String... args) throws Exception {

        println "--- running crud  runner ---"

        // first drop the database so that we can run this multiple times with the same dataset
        operations.dropDatabase()

        // save a single entity in the database
        // there is no need of creating the collection first. This happen automatically
        final Organisation bank = new Organisation(name: "NatWest", inaugurated: 1870, webAddress: "NatWest.com")
        orgRepo.save(bank)

        final Site s = new Site(name: "Mirfield Branch", org: bank)
        siteRepo.save(s)

        assert siteRepo.count() == 1

        OperatesFromSites owns = new OperatesFromSites(owningOrg: bank, site: s)  //create relationship
        ownsRepo.save(owns)
        println "saved owning site relationship as edge " + owns.dump()

        //bank.sites.add(s) //add site to bank and save
        //orgRepo.save(bank)  //- crashes infinite loop

        CollectionOperations orgColl = operations.collection(Organisation)
        CollectionOperations siteColl = operations.collection(Site)

        CollectionPropertiesEntity props = siteColl.getProperties()
        println "collection ('organisation') with name : " + props.name

        props = siteColl.getProperties()
        println "collection ('site') with name : " + props.name

        // the generated id from the database is set in the original entity
        println(String.format("bank '%s' organisation saved in the database with id: '%s'", bank.name, bank.id))

        // create an example from saved object and use to query the db - findOne returns Optional<T>
        //Optional<Organisation> res = orgRepo.findOne(Example.of(bank))
        //Optional<Organisation> res = orgRepo.findByName("NatWest")
        Optional<Organisation> res = orgRepo.findById(bank.id)
        assert res.isPresent()

        final Organisation foundOrg = res.get()

        orgRepo.findOne()
        println(String.format("Found org with name : %s and id %s", foundOrg.name, foundOrg.id))


        println "create and save some orgs "
        orgRepo.saveAll(CrudRunner.createOrgs())

        println "create and save some sites  "
        siteRepo.saveAll(CrudRunner.createSites())


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

