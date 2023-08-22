package com.famy.us.feature.registration.machine.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.machines.CoroutineMachineState
import com.famy.us.domain.usecase.GetCurrentMemberUseCase
import com.famy.us.feature.registration.machine.RegistrationScreenIntent
import com.famy.us.feature.registration.machine.RegistrationScreenState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Machine state for when the current member is being loaded.
 */
internal class LoadingMemberState<Event : RegistrationScreenIntent,
    State : RegistrationScreenState,> : CoroutineMachineState<Event, State>(), KoinComponent {

    private val getCurrentMemberUseCase: GetCurrentMemberUseCase by inject()

    override fun doStart() {
        super.doStart()
        logD { "Loading Member State" }
        setUiState(RegistrationScreenState.Idle as State)
        load()
    }

    private fun load() {
        machineScope.launch {
            getCurrentMemberUseCase().collect { member ->
                logD { "Member : $member" }
                if (member == null) {
                    setMachineState(ShowRegistrationContentState())
                } else {
                    setMachineState(FinishRegistrationState())
                }
            }
        }
    }
}
