package com.famy.us.feature.home.model

import androidx.compose.runtime.Composable
import com.famy.us.core.utils.navigation.Destination
import com.famy.us.core.utils.navigation.Navigator
import com.famy.us.core.utils.resources.IconResource

/**
 * Class to provide all menus available in the home screen with they data necessary to build each
 * one.
 *
 * @property name is the hint name in the menu.
 * @property destination is the route to goes when menu item being clicked.
 * @property priority the priority that need be shown.
 * @property icon the icon that will be shown.
 * @property onSelectIcon the icon to show when the menu is selected.
 * @property screen the corresponding screen for this menu.
 */
data class MenuItem(
    val name: String,
    val destination: Destination,
    val isClicked: Boolean = false,
    val priority: Int,
    val icon: IconResource,
    val onSelectIcon: IconResource,
    val screen: @Composable (onNavigate: (Navigator) -> Unit) -> Unit,
)
