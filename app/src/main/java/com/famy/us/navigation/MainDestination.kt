package com.famy.us.navigation

import com.famy.us.core.utils.navigation.Destination

/**
 * Object representing all destination possible from the main screen.
 */
object MainDestination {

    /**
     * Object representing the menu destination.
     */
    object Menu : Destination("menus/{menu}")
}
