package com.softwood.arango

import com.softwood.arango.model.Organisation
import com.softwood.arango.model.Site
import com.softwood.arango.relationships.OperatesFromSites
import com.softwood.arango.repository.OperatesFromSitesRelationshipRepository
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
    private OperatesFromSitesRelationshipRepository ownsRepo

    @Override
    public void run(final String... args) {
        println "--- running relations  runner ---"

        println "query for HSBC and find "
        Optional<Organisation> res = orgsRepo.findByName("HSBC")
        res.ifPresent({ bank ->
            Optional<Site> s = sitesRepo.findByName("Canary wharf, HQ")
            s.ifPresent({ site ->
                ownsRepo.save(new OperatesFromSites(owningOrg: bank, site: site))
            })
        })

        //Optional<Organisation> anotherRes = orgsRepo.findByName("xxx")
        println "ownsRepo contains " + ownsRepo.count()
        println res.get().sites
    }
}
