package com.famy.us.feature.note

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notes
import com.famy.us.core.utils.navigation.Destination
import com.famy.us.core.utils.resources.IconResource
import com.famy.us.feature.home.MenusLoader
import com.famy.us.feature.home.model.MenuItem
import com.famy.us.feature.note.notescreen.NoteMenuScreenContainer

/**
 * Class responsible to load the menu for the note feature.
 */
internal class NoteMenuLoader : MenusLoader {

    val tempDest = object : Destination("") {}

    override fun loadMenu(): MenuItem = MenuItem(
        name = "Note",
        destination = tempDest,
        priority = 1,
        icon = IconResource.fromImageVector(Icons.Rounded.Notes),
        onSelectIcon = IconResource.fromImageVector(Icons.Rounded.Notes),
        screen = { NoteMenuScreenContainer(onNavigate = it) },
    )
}
