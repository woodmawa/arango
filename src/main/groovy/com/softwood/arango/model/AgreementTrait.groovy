package com.softwood.arango.model

import java.time.Duration

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

//see above note
trait AgreementTermOrCondition {
    String number
    String description
    Duration timePeriod
}