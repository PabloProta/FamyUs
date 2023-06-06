package com.famy.us

sealed class HomeState {
    /**
     * For when the counter don't have any value.
     */
    object Idle : HomeState()

    object HomeMenu : HomeState()

    object NoteMenu : HomeState()

    object AddHomeTask : HomeState()
}
