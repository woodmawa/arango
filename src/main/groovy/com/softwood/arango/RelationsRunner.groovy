package com.softwood.arango

import com.softwood.arango.model.Organisation
import com.softwood.arango.relationships.OperatesFromMany
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
    public void run(final String... args) {
        println "--- running relations  runner ---"

        println "create and save some orgs "
        orgsRepo.saveAll(CrudRunner.createOrgs())

        println "create and save some sites  "
        sitesRepo.saveAll(CrudRunner.createSites())

        println "query for HSBC and find "
        Optional<Organisation> res = orgsRepo.findByName("HSBC")
        res.ifPresent({ bank ->
            Optional<Organisation> s = sitesRepo.findByName("Canary wharf, HQ")
            s.ifPresent({ site ->
                ownsRepo.save(new OperatesFromMany(owningOrg: bank, site: site))
            })
        })

        Collection<Organisation> anotherRes = orgsRepo.findByName("xxx")
        println "ownsRepo contains " + ownsRepo.count()
        println res.get().sites
    }
}
