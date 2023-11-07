package com.famy.us.core.utils.navigation

/**
 * Class represent a generic destiny to be use by destination.
 *
 * @property route the route name that the navigator will make the navigation.
 */
abstract class Destination(
    private val route: String,
    vararg params: String,
) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/${it}") }
        builder.toString()
    }
}
