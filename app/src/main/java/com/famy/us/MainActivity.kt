package com.famy.us

import FamyUsTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.famy.us.core.utils.navigation.composable
import com.famy.us.core.utils.navigation.doAction
import com.famy.us.core.utils.navigation.navigation
import com.famy.us.feature.home.MenusLoader
import com.famy.us.feature.note.createNote.CreateNoteScreenProvider
import com.famy.us.feature.note.navigation.NoteMenuNavigation
import com.famy.us.feature.note.taskContent.TaskContentProvider
import com.famy.us.invite.InviteScreenContainer
import com.famy.us.invite.navigation.InviteScreenNavigation
import com.famy.us.navigation.MainDestination
import com.famy.us.navigation.MenusRouterNavigation
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    private val menus = getKoin().getAll<MenusLoader>().map { it.loadMenu() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()
        setContent {
            FamyUsTheme {
                AppContainer()
            }
        }
    }

    @Composable
    @Suppress("LongMethod")
    fun AppContainer() {
        val navController = rememberNavController()

        NavHost(
            navController,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            startDestination = MainDestination.Menu.fullRoute,
        ) {
            composable(
                destination = MainDestination.Menu,
                enterTransition = slideIn(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioLowBouncy,
                    ),
                    initialOffset = { IntOffset(-it.width / 2, 100) },
                ),
            ) { backstackEntry ->
                val popupAt = backstackEntry.arguments?.getString("menu")
                MenusRouterNavigation(
                    menus = menus,
                    startDestination = popupAt,
                    onNavigate = { navController.doAction(it) },
                )
            }

            composable(
                destination = NoteMenuNavigation.NoteContent(),
                arguments = listOf(
                    navArgument("taskId") {
                        type = NavType.IntType
                    },
                ),
                enterTransition = slideIn(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioLowBouncy,
                    ),
                    initialOffset = { IntOffset(-it.width / 4, 100) },
                ),
            ) { backstackEntry ->
                val noteIdentifier: Int = backstackEntry.arguments?.getInt("taskId") ?: -1
                TaskContentProvider(noteIdentifier) { onNavigateDestination ->
                    navController.doAction(onNavigateDestination)
                }
            }

            composable(
                destination = NoteMenuNavigation.CreateTask,
                enterTransition = slideIn(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioLowBouncy,
                    ),
                    initialOffset = { IntOffset(-it.width / 4, 100) },
                ),
            ) {
                CreateNoteScreenProvider(onNavigate = { navController.doAction(it) })
            }

            navigation(
                route = MainDestination.Invite,
                startDestination = InviteScreenNavigation.InviteScreen,
            ) {
                composable(destination = InviteScreenNavigation.InviteScreen) {
                    InviteScreenContainer()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        FamyUsTheme {
            AppContainer()
        }
    }
}
