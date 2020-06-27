package com.softwood.arango.model

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.HashIndex
import com.arangodb.springframework.annotation.Relations
import com.softwood.arango.relationships.HasContract
import com.softwood.arango.relationships.OperatesFromSites
import com.softwood.arango.repository.OperatesFromSitesRelationshipRepository
import com.softwood.arango.repository.OrganisationRepository
import com.softwood.arango.repository.SiteRepository
import groovy.transform.EqualsAndHashCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id

import java.time.LocalDateTime

@Document("customers")
@HashIndex(fields = ["name"], unique = true)
@EqualsAndHashCode
class Customer extends PartyRole {

    @Autowired
    OperatesFromSitesRelationshipRepository orgSiteEdgeRepo

    @Autowired
    SiteRepository siteRepo

    @Id
    private String id

    String name
    String description
    String webAddress

    LocalDateTime dateSigned
    LocalDateTime createdDate

    @Relations(edges = HasContract.class, maxDepth = 1, direction = Relations.Direction.ANY, lazy = true)
    Collection<Contract> contracts = []

    void addSite (Site site) {
        if (organisation) {
            if (!site.id) {
                def res = siteRepo?.save(site)
                res
            }
            organisation.sites.add (site)
            assert orgSiteEdgeRepo

            //create edge to link the current customer to the site, and save the edge
            orgSiteEdgeRepo.save(new OperatesFromSites(from: this, to:site))
        }
    }

}
