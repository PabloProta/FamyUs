package com.famy.us.core.utils.navigation

/**
 * Class representing the action that could handled by the navigator.
 */
sealed interface Navigator {

    /**
     * Action representing the navigation to a destination.
     *
     * @property destination the destination that the navigation will popup.
     */
    data class NavigateTo(val destination: Destination) : Navigator

    /**
     * Action to pop the current destination to the previous one on the backstack.
     */
    object PopBackStack : Navigator
}
