package com.famy.us.core.utils.navigation

import kotlinx.serialization.Serializable

/**
 * Class represent a generic destiny to be used by destination.
 *
 * @property route the route name that the navigator will make the navigation.
 */
abstract class Destination private constructor(
    private val route: String,
    private val params: List<String>,
) {

    constructor(route: String, vararg params: String) : this(route, params.toList())

    val fullRoute: String = if (params.isEmpty()) {
        route
    } else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/$it") }
        builder.toString()
    }
}
