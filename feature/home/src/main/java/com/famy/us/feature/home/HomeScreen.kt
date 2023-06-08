package com.famy.us.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import org.koin.compose.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val menuLoaders: List<MenusLoader> = getKoin().getAll()
    val menus = menuLoaders.map {
        it.loadMenu()
    }

    Scaffold(
        modifier = Modifier,
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
            startDestination = menus.first { it.priority == 0 }.route,
            Modifier.padding(contentPadding),
        ) {
            menus.forEach { menu ->
                composable(menu.route) { menu.screen.invoke() }
            }
        }
    }
}
