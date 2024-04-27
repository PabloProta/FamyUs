package com.famy.us.feature.home.home

/**
 * Class to load all badges.
 */
internal object FilterBadgesLoader {

    /**
     * Method to load the badges.
     */
    fun load(): List<FilterBadgesState> = listOf(
        FilterBadgesState(
            name = "Tarefas de hoje",
            badge = FilterBadges.TODAY,
        ),
        FilterBadgesState(
            name = "Concluído",
            badge = FilterBadges.COMPLETED,
        ),
        FilterBadgesState(
            name = "Late",
            badge = FilterBadges.LATE,
        ),
        FilterBadgesState(
            name = "In progress",
            badge = FilterBadges.IN_PROGRESS,
        ),
        FilterBadgesState(
            name = "Total",
            badge = FilterBadges.TOTAL,
        ),
    )
}
