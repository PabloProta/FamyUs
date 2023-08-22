package com.famy.us.feature.note.states

import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.NoteScreenIntent
import com.famy.us.feature.note.NoteScreenState
import com.famy.us.feature.note.ShowDialog

/**
 * State for the current state machine for when user clicks in some task
 * card.
 *
 * @property task the task that will be shown its content.
 */
internal class ShowTaskContentState<Event : NoteScreenIntent, State : NoteScreenState>(
    private val task: HomeTask,
) : CommonMachineState<Event, State>() {

    override fun doStart() {
        val currentState = getUiState()
        val newState = currentState.copy(
            managingTask = task,
            showDialog = ShowDialog(
                shouldShowDialog = true,
                isAddingTask = false,
            ),
        )
        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)

        when (gesture) {
            is NoteScreenIntent.EditTask -> {
                setMachineState(EditingStateTask(task = gesture.task))
            }
            NoteScreenIntent.DismissDialog -> {
                setMachineState(ItemStateList(getUiState().listTask))
            }
        }
    }
}
