package com.famy.us.feature.note

import com.famy.us.domain.model.HomeTask

internal sealed class NoteScreenState {
    /**
     * For when the counter don't have any value.
     */
    object Idle : NoteScreenState()

    data class NoteScreenMenu(val listTask: List<HomeTask>) : NoteScreenState()

    object LoadingTasks : NoteScreenState()

    object AddingTask : NoteScreenState()
}
