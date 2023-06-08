package com.famy.us.feature.home.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Class to provide all menus available in the home screen with they data necessary to build each
 * one.
 *
 * @property name is the hint name in the menu.
 * @property route is the route to goes when menu item being clicked.
 * @property priority the priority that need be shown.
 * @property icon the icon that will be shown.
 * @property screen the corresponding screen for this menu.
 */
data class MenuItem(
    val name: String,
    val route: String,
    val priority: Int,
    val icon: ImageVector,
    val screen: @Composable () -> Unit
)
