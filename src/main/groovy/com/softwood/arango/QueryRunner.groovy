package com.softwood.arango

import com.arangodb.springframework.core.ArangoOperations
import com.softwood.arango.model.Organisation
import com.softwood.arango.repository.OperatesFromManyRepository
import com.softwood.arango.repository.OrganisationRepository
import com.softwood.arango.repository.SiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.domain.Sort

@ComponentScan("com.softwood.arango")
class QueryRunner implements CommandLineRunner{
    @Autowired
    private ArangoOperations operations
    @Autowired
    private OrganisationRepository orgRepo

    @Autowired
    private SiteRepository siteRepo

    @Autowired
    private OperatesFromManyRepository ownsRepo  //edge relationship


    @Override
    public void run (final String... args) {
        println "--- running  query runner ---"

        long orgCount
        println "numbers of organisations : " + (orgCount = orgRepo.count())

        //query using the operations bean
        long count = operations.collection(Organisation.class).count()
        assert count == orgCount

        println("## Return all Organisations sorted by name")
        Iterable<Organisation> allSorted = orgRepo.findAll(Sort.by(Sort.Direction.ASC, "name"))
        allSorted.forEach(/*System::println*/ {println it})
    }
}
