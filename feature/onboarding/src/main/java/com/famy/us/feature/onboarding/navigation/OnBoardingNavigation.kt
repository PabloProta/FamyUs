package com.famy.us.feature.onboarding.navigation

import com.famy.us.core.utils.navigation.Destination

/**
 * Object representing all destination possibles by the Authentication Flow.
 */
sealed interface OnBoardingNavigation {

    /**
     * Destination for authentication flow.
     */
    data object Authentication : Destination("authentication")
}
