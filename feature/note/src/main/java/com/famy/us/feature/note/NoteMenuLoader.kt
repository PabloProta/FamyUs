package com.famy.us.feature.note

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NoteAdd
import com.famy.us.feature.home.MenusLoader
import com.famy.us.feature.home.model.MenuItem
import com.famy.us.feature.note.notescreen.NoteMenuScreenContainer

/**
 * Class responsible to load the menu for the note feature.
 */
internal class NoteMenuLoader : MenusLoader {

    override fun loadMenu(): MenuItem = MenuItem(
        name = "Note",
        route = "note",
        priority = 1,
        icon = Icons.Rounded.NoteAdd,
        screen = { NoteMenuScreenContainer(onNavigate = it) },
    )
}
