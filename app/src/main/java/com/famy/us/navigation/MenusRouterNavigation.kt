package com.famy.us.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.famy.us.feature.home.model.MenuItem

@Composable
internal fun MenusRouterNavigation(
    menus: List<MenuItem>,
    startDestination: String?,
    onNavigate: (String) -> Unit,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                menus
                    .sortedBy { it.priority }
                    .forEach { menuItem ->
                        val selected =
                            currentDestination?.hierarchy?.any { menuItem.route == it.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(menuItem.route)
                            },
                            label = {
                                Text(
                                    text = menuItem.name,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = menuItem.icon,
                                    contentDescription = "${menuItem.name} Icon",
                                )
                            },
                        )
                    }
            }
        },
    ) { contentPadding ->
        NavHost(
            navController,
            startDestination = startDestination ?: menus
                .sortedBy { it.priority }
                .first()
                .route,
            Modifier.padding(contentPadding),
        ) {
            menus.forEach { menuItem ->
                composable(route = menuItem.route) {
                    menuItem.screen(onNavigate = onNavigate)
                }
            }
        }
    }
}
