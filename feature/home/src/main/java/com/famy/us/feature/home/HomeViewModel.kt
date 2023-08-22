package com.famy.us.feature.home

import androidx.lifecycle.ViewModel
import com.famy.us.domain.usecase.IsUserRegisteredUseCase
import com.famy.us.feature.home.model.MenuItem

/**
 * ViewModel for the Home Screen.
 */
class HomeViewModel(
    private val isUserRegisteredUseCase: IsUserRegisteredUseCase,
    private val allMenus: List<MenusLoader>,
) : ViewModel() {

    /**
     * Method to know if the user is registered
     */
    fun isUserRegistered(): Boolean = isUserRegisteredUseCase()

    /**
     * Method to get all menus for the tab navigation.
     */
    fun getMenus(): List<MenuItem> = allMenus.map {
        it.loadMenu()
    }
}
