package com.famy.us.feature.home

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.famy.us.authentication.AuthenticationContainer
import com.famy.us.feature.home.model.MenuItem
import com.famy.us.feature.registration.RegistrationScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val isMemberRegistered by remember {
        viewModel.hasUserRegistered
    }

    AuthenticationContainer {
        if (!viewModel.hasMemberInternetConnection()) {
            NoInternet()
        } else {
            if (isMemberRegistered) {
                RouterNavigation(menus = viewModel.getMenus())
            } else {
                RegistrationScreen(
                    onFinishRegistration = {
                        viewModel.checkMemberRegistered()
                    },
                )
            }
        }
    }
}

@Composable
fun NoInternet() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(text = "Sem conexão com a internet impossível sincronizar!")
    }
}

@Composable
fun RouterNavigation(menus: List<MenuItem>) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
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
            startDestination = menus
                .sortedBy { it.priority }
                .first().route,
            Modifier.padding(contentPadding),
        ) {
            menus.forEach { menu ->
                composable(menu.route) { menu.screen.invoke() }
            }
        }
    }
}
