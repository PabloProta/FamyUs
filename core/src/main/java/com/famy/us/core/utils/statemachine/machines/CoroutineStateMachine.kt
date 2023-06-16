package com.famy.us.core.utils.statemachine.machines

import com.famy.us.core.utils.statemachine.states.UiEvent
import com.famy.us.core.utils.statemachine.states.UiSate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * State Machine to use with coroutines, with own scope.
 */
abstract class CoroutineStateMachine<Event : UiEvent, State : UiSate> :
    CommonStateMachine<Event, State>() {

    /**
     * The scope used.
     */
    protected val machineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun doClear() {
        super.doClear()
        machineScope.cancel()
    }
}
