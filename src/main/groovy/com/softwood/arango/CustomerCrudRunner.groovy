package com.softwood.arango

import com.arangodb.entity.CollectionPropertiesEntity
import com.arangodb.springframework.core.ArangoOperations
import com.arangodb.springframework.core.CollectionOperations
import com.softwood.arango.model.Contract
import com.softwood.arango.model.Customer
import com.softwood.arango.model.Organisation
import com.softwood.arango.model.PartyRole
import com.softwood.arango.model.Site
import com.softwood.arango.relationships.HasContract
import com.softwood.arango.repository.ContractRepository
import com.softwood.arango.repository.CustomerRepository
import com.softwood.arango.repository.HasContractRelationshipRepository
import com.softwood.arango.repository.OperatesFromManyRepository
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
    private OperatesFromManyRepository ownsRepo  //edge relationship


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

        println "--- running customer crud  runner ---"

        // first drop the database so that we can run this multiple times with the same dataset
        //operations.dropDatabase()

        //crud runner will run these
        //createOrgs()
        //createSites()

        custRepo.saveAll(createCustomers(orgRepo))
        Customer cust = custRepo.findByName("HSBC")

        Contract firstContract =  new Contract (name: "first contract", dateSigned: LocalDateTime.now(), documentNumber: "contract/1/1", statementOfIntent: "opening gambit")
        contractRepo.save (firstContract)

        HasContract hasContract = new HasContract (owningCustomer: cust, contract: firstContract)
        hasContractsRelRepo.save (hasContract)

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

        final Customer foundCust = res

        println(String.format("Found customer with name : %s and id %s", foundCust.name, foundCust.id))

        //assert fails as proxy doesnt do eager select, so we have get The Contracts to
        //force the read

        def conList = foundCust.getContracts()
        println "foundCust has contracts list of size :  ${foundCust.contracts.size ()}"

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

