package com.famy.us.feature.registration.navigation

import com.famy.us.core.utils.navigation.Destination

/**
 * Object representing all destination possibles by the Authentication Flow.
 */
object RegistrationNavigation {
    /**
     * The route for the registration navigation.
     */
    object ROUTE : Destination("create_account")

    /**
     * Destination responsible to route the screen between enter the family and create a new one.
     */
    object CreatingFamilyRouter : Destination("creating_family_router")

    /**
     * Destination to enter in a existent family.
     */
    object EnterFamily : Destination("enter_family")

    /**
     * Destination to insert the user info while is creating a new family.
     */
    object InsertMemberInfo : Destination("insert_member_info")

    /**
     * Destination to insert the family name.
     */
    object InsertFamilyName : Destination("insert_family_name")

    /**
     * Screen to enter the family code.
     */
    object EnterFamilyCode : Destination("enter_family_code")
}
