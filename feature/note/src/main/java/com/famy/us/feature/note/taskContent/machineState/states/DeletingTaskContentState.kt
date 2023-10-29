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
 * Machine state for when the user are editing the task content.
 */
internal class DeletingTaskContentState<
    Event : TaskContentScreenIntent, State : TaskContentScreenState,
    >
(private val taskId: Int) : CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

    override fun doStart() {
        super.doStart()
        logD { "DeletingTaskContentState" }
        deleteTask()
    }

    private fun deleteTask() {
        machineScope.launch {
            homeTaskRepository.deleteTaskById(taskId)
            val currentState = getUiState()
            setUiState(
                currentState.copy(
                    deleting = true,
                ) as State,
            )
        }
    }
}
