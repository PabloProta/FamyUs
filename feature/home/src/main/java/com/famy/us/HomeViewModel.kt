package com.famy.us

import androidx.lifecycle.ViewModel
import com.famy.us.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Idle)
    val uiState = _uiState.asStateFlow()
    val menus = listOf(
        MenuItem.Home,
        MenuItem.Note,
    )

    fun perform(event: HomeIntent) {
        val currentState = _uiState.value
        when (event) {
            HomeIntent.AddTask -> {}
            HomeIntent.EditTask -> {}
            HomeIntent.DeleteTask -> {}
            HomeIntent.LoadTasks -> {}
        }
    }
}
