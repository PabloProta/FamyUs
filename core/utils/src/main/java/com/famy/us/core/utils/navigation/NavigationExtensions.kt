package com.famy.us.core.utils.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

/**
 * Method to set a composable for the nav host based on [Destination].
 *
 * @param destination the [Destination] of this composable.
 * @param enterTransition the animation for when app is enter on the composable.
 * @param exitTransition the animation for when app is exiting from the composable.
 * @param arguments the arguments fot this composable.
 * @param deepLinks deeps links for this composable.
 * @param content the content that will be on this composable.
 */
fun NavGraphBuilder.composable(
    destination: Destination,
    enterTransition: EnterTransition = EnterTransition.None,
    exitTransition: ExitTransition = ExitTransition.None,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit),
) {
    composable(
        route = destination.fullRoute,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        arguments = arguments,
        deepLinks = deepLinks,
        content = content
    )
}


/**
 * Method to make an action with the navcontroller based on a [Navigator]
 * action.
 *
 * @param navigator the object that will tell what navcontroller should does.
 */
fun NavController.doAction(navigator: Navigator) {
    when (navigator) {
        is Navigator.NavigateTo -> navigate(navigator.destination)
        Navigator.PopBackStack -> popBackStack()
    }
}

/**
 * Method to call the navigate with a destination object.
 *
 * @param destination the destination that app should navigate.
 */
fun NavController.navigate(destination: Destination) {
    navigate(route = destination.fullRoute)
}
