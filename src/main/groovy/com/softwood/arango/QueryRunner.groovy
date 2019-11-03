package com.softwood.arango

import com.arangodb.springframework.core.ArangoOperations
import com.softwood.arango.repository.OperatesFromManyRepository
import com.softwood.arango.repository.OrganisationRepository
import com.softwood.arango.repository.SiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan

@ComponentScan("com.softwood.arango")
class QueryRunner {
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

        println "numbers of organisations : " + orgRepo.count()

    }
}
