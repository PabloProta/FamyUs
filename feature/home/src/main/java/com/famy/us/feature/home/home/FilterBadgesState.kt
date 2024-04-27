package com.famy.us.feature.home.home

/**
 * Model used to represent the states for the filter badges localized on Admin Home Screen.
 *
 * @property name the name of the badge
 * @property badge the badge itself
 * @property isSelected if the badge was selected
 */
internal data class FilterBadgesState(
    val name: String,
    val badge: FilterBadges,
    val isSelected: Boolean = false,
)
