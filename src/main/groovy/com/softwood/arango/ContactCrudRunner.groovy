package com.softwood.arango

import com.arangodb.ArangoCursor
import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import com.arangodb.entity.CollectionPropertiesEntity
import com.arangodb.springframework.core.ArangoOperations
import com.arangodb.springframework.core.CollectionOperations
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module
import com.softwood.arango.model.*
import com.softwood.arango.model.contact.Contact
import com.softwood.arango.relationships.HasContract
import com.softwood.arango.repository.*
import com.softwood.arango.repository.contact.ContactRepository
import com.softwood.arango.repository.contact.UseContactMediumRelationshipRepository
import com.softwood.arango.services.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.ComponentScan

import java.time.LocalDateTime

@ComponentScan("com.softwood.arango")
public class ContactCrudRunner implements CommandLineRunner {

    @Autowired
    private ArangoOperations operations
    @Autowired
    private ContactRepository contactRepo

    @Autowired
    private UseContactMediumRelationshipRepository hasContactMediumRelationshipRepo  //edge relationship

    @Autowired
    private OperatesFromSitesRelationshipRepository ownsRepo  //edge relationship


    static Collection<Contact> createContacts(ContactRepository contactRepo) {

        Arrays.asList(
                new Contact(firstName: "Will", surName: "Woodman", createdDate: LocalDateTime.now(), role: PartyRole.RoleType.Customer),
                new Contact(firstName: "Marian", surName: "Woodman", createdDate: LocalDateTime.now(), role: PartyRole.RoleType.Customer),
                new Contact(firstName: "Dominic", surName: "Woodman", createdDate: LocalDateTime.now(), role: PartyRole.RoleType.Customer)
        )
    }



    @Override
    public void run(final String... args) throws Exception {

        println "--- running Contact crud  runner ---"

        // first drop the database so that we can run this multiple times with the same dataset
        //operations.dropDatabase()

        //crud runner will run these
        //createOrgs()
        //createSites()

        contactRepo.saveAll(createContacts(contactRepo))
        List contacts = contactRepo.findBySurName("Woodman")


        CollectionOperations contactsColl = operations.collection(Contacts)

        CollectionPropertiesEntity props = contactsColl.getProperties()
        println "collection ('contact') with name : " + props.name


        assert contacts
        assert contacts.size() == 4
        println "${contacts.size()} Woodman's were found"
    }


}

