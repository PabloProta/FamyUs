package com.famy.us.feature.note.notescreen.machinestate.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CoroutineMachineState
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * This is the State for the state machine for when the user are
 * adding some task.
 *
 */
internal class AddStateTask<Event : NoteScreenIntent, State : NoteScreenState> :
    CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

    override fun doStart() {
        super.doStart()
        logD { "AddTaskState" }

        val newState = getUiState().copy(
            isAddingTask = true,
        )
        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        val currentState = getUiState()
        when (gesture) {
            is NoteScreenIntent.DismissTaskContent -> {
                setMachineState(ItemStateList(currentState.showingTaskList))
            }

            is NoteScreenIntent.SaveTask -> {
                machineScope.launch {
                    currentState.apply {
                        val currentList = showingTaskList
                        val lastPosition = if (currentList.isEmpty()) {
                            0
                        } else {
                            currentList.last().position + 1
                        }
                        homeTaskRepository.saveTask(gesture.task.copy(position = lastPosition))
                        setMachineState(LoadingState(justFetch = true))
                    }
                }
            }

            NoteScreenIntent.DoBack -> setMachineState(ItemStateList(currentState.showingTaskList))
        }
    }
}
