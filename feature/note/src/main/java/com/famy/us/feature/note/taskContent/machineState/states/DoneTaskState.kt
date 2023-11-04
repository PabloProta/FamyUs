package com.famy.us.feature.note.taskContent.machineState.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.machines.CoroutineMachineState
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenIntent
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Machine state for when the user finished the task.
 */
internal class DoneTaskState<Event : TaskContentScreenIntent, State : TaskContentScreenState> :
    CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

    override fun doStart() {
        super.doStart()
        logD { "DoneTaskState" }
        val currentState = getUiState()
        val newState = currentState.copy(
            finishing = true,
        )
        finishTask()
        setUiState(newState as State)
    }

    private fun finishTask() {
        val currentNote = getUiState().onContent ?: return
        machineScope.launch {
            homeTaskRepository.deleteTaskById(currentNote.id)
        }
    }
}
