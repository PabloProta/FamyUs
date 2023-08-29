package com.famy.us.invite

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famy.us.domain.usecase.GetFamilyInviteUseCase
import kotlinx.coroutines.launch

/**
 * View model for the invite screen.
 */
class InviteScreenViewModel(
    getFamilyInviteUseCase: GetFamilyInviteUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch {
            getFamilyInviteUseCase().collect {
                familyInviteScreen.value = it
            }
        }
    }

    /**
     * Variable to know the string related to the family invite to build the qr code.
     */
    val familyInviteScreen = mutableStateOf("")
}
