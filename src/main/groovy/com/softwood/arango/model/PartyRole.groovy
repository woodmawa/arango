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
    String href

    /**
     * corresponds to @att-name in tmf api models
     */
    String atBaseType
    String atSchemaLocation
    String atType

    @Ref (lazy = false)
    Organisation organisation
}
