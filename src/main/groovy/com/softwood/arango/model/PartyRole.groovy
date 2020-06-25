package com.softwood.arango.model

abstract class PartyRole {

    enum RoleType {
        Customer,
        Maintainer,
        ServiceProvider,
        Manufacturer
    }

    RoleType role
    Organisation organisation
}
