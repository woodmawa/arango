package com.softwood.arango.services

import com.softwood.arango.model.Customer
import com.softwood.arango.model.Site
import com.softwood.arango.relationships.OperatesFromSites
import com.softwood.arango.repository.CustomerRepository
import com.softwood.arango.repository.OperatesFromSitesRelationshipRepository
import com.softwood.arango.repository.SiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerService {

    @Autowired
    private OperatesFromSitesRelationshipRepository ownsRepo

    @Autowired
    private CustomerRepository custRepo

    @Autowired
    private SiteRepository siteRepo

    void addSite (Customer cust, Site site) {
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
