package com.famy.us

import FamyUsTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.famy.us.core.utils.navigation.composable
import com.famy.us.core.utils.navigation.doAction
import com.famy.us.feature.home.MenusLoader
import com.famy.us.feature.note.createNote.CreateNoteScreenProvider
import com.famy.us.feature.note.navigation.NoteMenuNavigation
import com.famy.us.feature.note.taskContent.TaskContentProvider
import com.famy.us.invite.InviteScreenContainer
import com.famy.us.navigation.MainDestination
import com.famy.us.navigation.MenusRouterNavigation
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    private val menus = getKoin().getAll<MenusLoader>().map { it.loadMenu() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            FamyUsTheme {
                AppContainer()
            }
        }
    }

    @Composable
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
            ) { backstackEntry ->
                val popupAt = backstackEntry.arguments?.getString("menu")
                MenusRouterNavigation(
                    menus = menus,
                    startDestination = popupAt,
                    onNavigate = { navController.doAction(it) },
                )
            }

            composable(
                destination = NoteMenuNavigation.NoteContent() ,
                arguments = listOf(
                    navArgument("taskId") {
                        type = NavType.StringType
                    },
                ),
            ) { backstackEntry ->
                val noteIdentifier: Int = backstackEntry.arguments?.getInt("taskId") ?: -1
                TaskContentProvider(noteIdentifier) { onNavigateDestination ->
                    navController.navigate(onNavigateDestination)
                }
            }

            composable("create_task") {
                CreateNoteScreenProvider(
                    onFinish = {
                        navController.navigate("menus/note") {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigate = { navController.doAction(it) },
                )
            }

            navigation(
                route = "invite",
                startDestination = "invite_screen",
            ) {
                composable("invite_screen") {
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
