package com.famy.us.feature.note.notescreen.machinestate.states

import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CoroutineMachineState
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * State for the current state machine for when user is editing some task.
 *
 * @property task the task that are being edited.
 */
internal class EditingStateTask<Event : NoteScreenIntent, State : NoteScreenState>(
    private val task: HomeTask,
) : CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

    override fun doStart() {
        val currentState = getUiState()
        val newState = currentState.copy(
            editingTask = task,
        )

        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        val currentState = getUiState()
        when (gesture) {
            is NoteScreenIntent.SaveTask -> {
                machineScope.launch {
                    homeTaskRepository.updateTask(task = gesture.task)
                    setMachineState(LoadingState(justFetch = true))
                }
            }
            NoteScreenIntent.DismissTaskContent -> {
                setMachineState(ItemStateList(currentState.showingTaskList))
            }
        }
    }
}
