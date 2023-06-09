package com.famy.us.feature.home

import com.famy.us.feature.home.model.MenuItem

/**
 * Interface to get all navigation menus.
 */
interface MenusLoader {

    /**
     * Function to get the menus from each module that implements.
     */
    fun loadMenu(): MenuItem
}
