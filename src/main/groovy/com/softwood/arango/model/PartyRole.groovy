package com.softwood.arango.model

import com.arangodb.springframework.annotation.Ref

abstract class PartyRole {

    enum RoleType {
        Customer,
        Maintainer,
        ServiceProvider,
        Manufacturer
    }

    RoleType role

    @Ref (lazy = false)
    Organisation organisation
}
