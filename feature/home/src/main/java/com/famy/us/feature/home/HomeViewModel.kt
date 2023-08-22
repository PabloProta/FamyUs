package com.famy.us.feature.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.connection.ConnectionChecker
import com.famy.us.domain.usecase.GetCurrentMemberUseCase
import com.famy.us.feature.home.model.MenuItem
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home Screen.
 *
 * @property getCurrentMemberUseCase use case to get the user already registered.
 * @property connectionChecker util to give information related to the device connection.
 * @property allMenus get all menus available on app.
 */
class HomeViewModel(
    private val getCurrentMemberUseCase: GetCurrentMemberUseCase,
    private val connectionChecker: ConnectionChecker,
    private val allMenus: List<MenusLoader>,
) : ViewModel() {

    init {
        viewModelScope.launch {
            hasUserRegistered.value = getCurrentMemberUseCase().last() != null
        }
    }

    /**
     * State to know if the user is already registered.
     */
    val hasUserRegistered: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Method to know if the member has internet connection.
     */
    fun hasMemberInternetConnection() = connectionChecker.hasInternetAccess()

    /**
     * Method to check the member after it being registered
     */
    fun checkMemberRegistered() {
        viewModelScope.launch {
            getCurrentMemberUseCase().collect { member ->
                logD { "member: $member" }
                if (member != null) {
                    hasUserRegistered.value = true
                    cancel()
                }
            }
        }
    }

    /**
     * Method to get all menus for the tab navigation.
     */
    fun getMenus(): List<MenuItem> = allMenus.map {
        it.loadMenu()
    }
}
