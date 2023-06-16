package com.famy.us.feature.note.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.statemachine.machines.CoroutineStateMachine
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.NoteScreenIntent
import com.famy.us.feature.note.NoteScreenState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * A class to represent the machine state for when the note screen is
 * loading something.
 */
internal class LoadingState<Event : NoteScreenIntent, State : NoteScreenState> :
    CoroutineStateMachine<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

    override fun doStart() {
        super.doStart()
        logD { "Loading State" }
        setUiState(NoteScreenState.Idle as State)
        load()
    }

    private fun load() {
        machineScope.launch {
            homeTaskRepository.getAllTask().collect {
                val newMachineState = ItemListState<Event, State>(it)
                setMachineState(newMachineState)
            }
        }
    }
}
