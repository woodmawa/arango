package com.softwood.arango

import com.softwood.arango.repository.OperatesFromManyRepository
import com.softwood.arango.repository.OrganisationRepository
import com.softwood.arango.repository.SiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner

class RelationsRunner implements CommandLineRunner {

    @Autowired
    private OrganisationRepository orgsRepo

    @Autowired
    private SiteRepository sitesRepo

    @Autowired
    private OperatesFromManyRepository ownsRepo

    @Override
    public void run (final String... args) {
        println "in RelationsRunner "

        println "create and save some orgs "
        orgsRepo (CrudRunner.createOrgs())

        println "create and save some sites  "
        sitesRepo (CrudRunner.createSites())

        println "query for HSBC and find "
        orgsRepo.findByName ("HSBC").each {println "${it.name} id[${it.id}]" }
    }
}
