package com.famy.us.feature.home

import com.famy.us.core.utils.navigation.Destination
import com.famy.us.core.utils.resources.IconResource
import com.famy.us.feature.home.model.MenuItem
import com.famy.us.home.R

/**
 * Class responsible to load the menu for the home feature.
 */
internal class HomeMenuLoader : MenusLoader {

    val tempDest = object : Destination("") {}

    override fun loadMenu(): MenuItem = MenuItem(
        name = "Home",
        destination = tempDest,
        priority = 0,
        icon = IconResource.fromDrawableResource(R.drawable.ic_box_outlined),
        onSelectIcon = IconResource.fromDrawableResource(R.drawable.ic_box_filled),
        screen = { HomeScreenContainer() },
    )
}
