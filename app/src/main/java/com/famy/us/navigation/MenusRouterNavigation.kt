package com.famy.us.navigation

import androidx.compose.runtime.Composable
import com.famy.us.core.utils.navigation.Navigator
import com.famy.us.feature.home.model.MenuItem

@Composable
@Suppress("LongMethod")
internal fun MenusRouterNavigation(
    menus: List<MenuItem>,
    startDestination: String?,
    onNavigate: (Navigator) -> Unit,
) {
//    val navController = rememberNavController()
//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = backStackEntry?.destination
//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize(),
//        bottomBar = {
//            NavigationBar(
//                containerColor = Color.Transparent,
//            ) {
//                menus
//                    .sortedBy { it.priority }
//                    .forEach { menuItem ->
//                        val selected =
//                            currentDestination?.hierarchy?.any { menuItem.route == it.route } == true
//                        NavigationBarItem(
//                            selected = selected,
//                            onClick = {
//                                navController.navigate(menuItem.route)
//                            },
//                            label = {
//                                Text(
//                                    text = menuItem.name,
//                                    fontWeight = FontWeight.SemiBold,
//                                )
//                            },
//                            icon = {
//                                Icon(
//                                    imageVector = menuItem.icon,
//                                    contentDescription = "${menuItem.name} Icon",
//                                )
//                            },
//                        )
//                    }
//            }
//        },
//    ) { contentPadding ->
//        NavHost(
//            navController,
//            enterTransition = { EnterTransition.None },
//            exitTransition = { ExitTransition.None },
//            startDestination = startDestination ?: menus
//                .sortedBy { it.priority }
//                .first()
//                .route,
//            modifier = Modifier.padding(contentPadding),
//        ) {
//            menus.forEach { menuItem ->
//                composable(
//                    route = menuItem.route,
//                    enterTransition = {
//                        slideInHorizontally(
//                            animationSpec = spring(
//                                stiffness = Spring.StiffnessLow,
//                                dampingRatio = Spring.DampingRatioMediumBouncy,
//                            ),
//                        )
//                    },
//                ) {
//                    menuItem.screen(onNavigate = onNavigate)
//                }
//            }
//        }
//    }
}
