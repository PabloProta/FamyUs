package com.famy.us.feature.note.notescreen.machinestate.states

import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenState

/**
 * State for the current state machine for when user clicks in some task
 * card.
 *
 * @property taskId the task that will be shown its content.
 */
internal class ShowTaskContentState<Event : NoteScreenIntent, State : NoteScreenState>(
    private val taskId: Int
) :
    CommonMachineState<Event, State>() {

    override fun doStart() {
        val currentState = getUiState()
        val newState = currentState.copy(
            goingToShowTaskContent = taskId
        )
        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)

        when (gesture) {
            is NoteScreenIntent.EditTask -> {
                setMachineState(EditingStateTask(task = gesture.task))
            }

            NoteScreenIntent.DismissTaskContent -> {
                setMachineState(ItemStateList(getUiState().showingTaskList))
            }
        }
    }
}
