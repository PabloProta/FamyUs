package com.famy.us.core.utils.machines

import com.famy.us.core.utils.UiEvent
import com.famy.us.core.utils.UiSate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * This is class to represent a state to a state machine to use with coroutines, with own scope.
 */
abstract class CoroutineMachineState<Event : UiEvent, State : UiSate> :
    CommonMachineState<Event, State>() {

    /**
     * The scope used.
     */
    protected val machineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun doClear() {
        super.doClear()
        machineScope.cancel()
    }
}
