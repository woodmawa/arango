package com.softwood.arango.model

/**
 * capability mixin capability
 */
trait Agreement {
    Collection agreementItems = []
    String agreementTermAndCondition  //todo : might need to be a type later

}

trait AgreementItem {
    Product product
}