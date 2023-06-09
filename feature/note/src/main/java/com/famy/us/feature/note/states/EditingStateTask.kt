package com.famy.us.feature.note.states

import com.famy.us.core.utils.statemachine.StateMachine
import com.famy.us.core.utils.statemachine.machines.CoroutineMachineState
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.NoteScreenIntent
import com.famy.us.feature.note.NoteScreenState
import com.famy.us.feature.note.ShowDialog
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
            managingTask = task,
            showDialog = ShowDialog(
                shouldShowDialog = true,
                isEditingTask = true,
                isAddingTask = false,
            ),
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
            NoteScreenIntent.DismissDialog -> {
                setMachineState(ItemStateList(currentState.listTask))
            }
        }
    }
}
