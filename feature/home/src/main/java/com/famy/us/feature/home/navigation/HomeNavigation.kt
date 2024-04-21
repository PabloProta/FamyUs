package com.famy.us.feature.home.navigation

import com.famy.us.core.utils.navigation.Destination

/**
 * Object representing all routes possibilities into the home.
 */
object HomeNavigation {

    /**
     * The route name.
     */
    object ROUTE : Destination("home")

    /**
     * Home screen navigation.
     */
    object HomeScreen: Destination("home_screen")
}
