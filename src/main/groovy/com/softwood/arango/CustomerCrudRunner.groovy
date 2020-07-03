package com.softwood.arango

import com.arangodb.ArangoCursor
import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import com.arangodb.entity.BaseDocument
import com.arangodb.entity.CollectionPropertiesEntity
import com.arangodb.entity.DocumentEntity
import com.arangodb.springframework.core.ArangoOperations
import com.arangodb.springframework.core.CollectionOperations
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module
import com.softwood.arango.model.Contract
import com.softwood.arango.model.Customer
import com.softwood.arango.model.Organisation
import com.softwood.arango.model.PartyRole
import com.softwood.arango.model.Site
import com.softwood.arango.relationships.HasContract
import com.softwood.arango.relationships.OperatesFromSites
import com.softwood.arango.repository.ContractRepository
import com.softwood.arango.repository.CustomerRepository
import com.softwood.arango.repository.HasContractRelationshipRepository
import com.softwood.arango.repository.OperatesFromSitesRelationshipRepository
import com.softwood.arango.repository.OrganisationRepository
import com.softwood.arango.repository.SiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.ComponentScan

import java.time.LocalDateTime

@ComponentScan("com.softwood.arango")
public class CustomerCrudRunner implements CommandLineRunner {

    @Autowired
    private ArangoOperations operations
    @Autowired
    private CustomerRepository custRepo

    @Autowired
    private OrganisationRepository orgRepo

    @Autowired
    private SiteRepository siteRepo


    @Autowired
    private ContractRepository contractRepo

    @Autowired
    private HasContractRelationshipRepository hasContractsRelRepo  //edge relationship

    @Autowired
    private OperatesFromSitesRelationshipRepository ownsRepo  //edge relationship


    static Collection<Organisation> createCustomers(OrganisationRepository orgRepo) {

        Optional<Organisation> vodafoneOrg = orgRepo.findByName("Vodafone")
        Optional<Organisation> btOrg = orgRepo.findByName("BT")
        Optional<Organisation> hsbcOrg = orgRepo.findByName("HSBC")

        Arrays.asList(
                new Customer(name: "Vodafone", createdDate: LocalDateTime.now(), role: PartyRole.RoleType.Customer, organisation: vodafoneOrg.get()),
                new Customer(name: "BT", createdDate: LocalDateTime.now(), role: PartyRole.RoleType.Customer, organisation: btOrg.get()),
                new Customer(name: "HSBC", createdDate: LocalDateTime.now(), role: PartyRole.RoleType.Customer, organisation: hsbcOrg.get())
        )
    }



    @Override
    public void run(final String... args) throws Exception {

        println "--- running customer crud  runner ---"

        // first drop the database so that we can run this multiple times with the same dataset
        //operations.dropDatabase()

        //crud runner will run these
        //createOrgs()
        //createSites()

        custRepo.saveAll(createCustomers(orgRepo))
        Customer cust = custRepo.findByName("HSBC")

        Contract firstContract =  new Contract (name: "first contract", dateSigned: LocalDateTime.now(), documentNumber: "contract/1/1", statementOfIntent: "opening gambit")
        def con1 = contractRepo.save (firstContract)

        HasContract hasContract = new HasContract (owningCustomer: cust, contract: firstContract)
        def conrel = hasContractsRelRepo.save (hasContract)

        CollectionOperations custColl = operations.collection(Customer)
        CollectionOperations orgColl = operations.collection(Organisation)
        CollectionOperations siteColl = operations.collection(Site)

        CollectionPropertiesEntity props = siteColl.getProperties()
        println "collection ('site') with name : " + props.name

        props = custColl.getProperties()
        println "collection ('customer') with name : " + props.name

        // the generated id from the database is set in the original entity
        //println(String.format("bank '%s' organisation saved in the database with id: '%s'", bank.name, bank.id))

        // create an example from saved object and use to query the db - findOne returns Optional<T>
        //Optional<Organisation> res = orgRepo.findOne(Example.of(bank))
        //Optional<Organisation> res = orgRepo.findByName("NatWest")
        Customer res = custRepo.findByName("HSBC")
        assert res
        assert res.contracts
        assert res.contracts.size() == 1

        final Customer foundCust = res

        println(String.format("Found customer with name : %s and id %s", foundCust.name, foundCust.id))

        Site s = new Site (name:'ipswich branch office')
        addSite(foundCust, s)
        assert foundCust.organisation.sites.size() == 2
        println "list of sites for $foundCust is ${foundCust.organisation.sites}"

        //If @relationship in customer is marked as lazy=true
        //force the read on the proxy to return the List of contracts as second query

        //def conList = foundCust.getContracts()
        println "foundCust has contracts list of size :  ${foundCust.contracts.size ()}"

        ArangoCursor<Customer> cursor = custRepo.custQueryByNameIlike("%SBC")


        List<Customer> custList = cursor.toList()

        custList

        /*def cres  = custRepo.customerListWithEmbeddedOrg ()

        def l = cres.toList()
        l*/

        //register LocaldateTime pack for velocty
        ArangoDB arango = new ArangoDB.Builder().registerModule(new VPackJdk8Module()).build()
        ArangoDatabase testDb = arango.db("testDB")


        String key = res.id - "customer/"

        def myObject = testDb.collection("customers").getDocument(key, String.class)
        ArangoCursor<Customer> dcursor = testDb.query("""for c in customers 
            for o in organisations 
                filter c.organisation == o._id
                return merge (c, {organisation: o})""",
            Customer /*String*/)

        List<Customer> lcust = dcursor.toList()
        def bd_hsbc = lcust?[2]
        Map bd_props = bd_hsbc.properties

        println "bd_props : $bd_props"

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

    void addSite (Customer cust,  Site site) {
        if (cust) {
            if (!cust.id) {
                custRepo.save (cust)
                assert cust.id
            }
            if (!site.id) {
                if (!site.org) {
                    site.org = cust.organisation
                }
                def res = siteRepo?.save(site)
                res
            }

            assert ownsRepo
            //create edge to link the current customer to the site, and save the edge
            OperatesFromSites ownsSiteLink = new OperatesFromSites(owningOrg: cust.organisation, site:site)
            def res = ownsRepo.save(ownsSiteLink)
            res
        }
    }
}

