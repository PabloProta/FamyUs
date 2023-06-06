package com.famy.us.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.NoteAdd
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Class to provide all menus available in the home screen with they data necessary to build each
 * one.
 *
 * @property name is the hint name in the menu.
 * @property route is the route to goes when menu item being clicked.
 * @property icon the icon that will be shown.
 */
sealed class MenuItem(
    val name: String,
    val route: String,
    val icon: ImageVector
) {
    object Home : MenuItem("Home", "home", Icons.Rounded.Home)

    object Note : MenuItem("Note", "note", Icons.Rounded.NoteAdd)
}
