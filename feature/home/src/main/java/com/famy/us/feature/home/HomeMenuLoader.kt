package com.famy.us.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import com.famy.us.feature.home.model.MenuItem

/**
 * Class responsible to load the menu for the home feature.
 */
internal class HomeMenuLoader : MenusLoader {

    override fun loadMenu(): MenuItem = MenuItem(
        name = "Home",
        route = "home",
        priority = 0,
        icon = Icons.Rounded.Home,
        screen = { HomeScreenContainer(onNavigate = it) },
    )
}
