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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.famy.us.authentication.LoginScreen
import com.famy.us.authentication.PreLoginScreen
import com.famy.us.authentication.navigation.AuthenticationNavigation
import com.famy.us.core.utils.navigation.composable
import com.famy.us.core.utils.navigation.doAction
import com.famy.us.core.utils.navigation.navigate
import com.famy.us.core.utils.navigation.navigation
import com.famy.us.feature.home.MenusLoader
import com.famy.us.feature.note.createNote.CreateNoteScreenProvider
import com.famy.us.feature.note.navigation.NoteMenuNavigation
import com.famy.us.feature.note.taskContent.TaskContentProvider
import com.famy.us.feature.onboarding.opening.OpeningScreenContainer
import com.famy.us.feature.registration.InsertFamilyNameScreen
import com.famy.us.feature.registration.CreatingAccountRouterScreen
import com.famy.us.feature.registration.EnterFamilyScreen
import com.famy.us.authentication.QrCodeScreen
import com.famy.us.authentication.forgotpassword.ForgotPasswordScreen
import com.famy.us.authentication.forgotpassword.InsertEmailCodeScreen
import com.famy.us.feature.registration.RegisterPersonalInfoScreen
import com.famy.us.feature.registration.navigation.RegistrationNavigation
import com.famy.us.invite.InviteScreenContainer
import com.famy.us.invite.navigation.InviteScreenNavigation
import com.famy.us.navigation.MainDestination
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
        // TODO - Workaround to the top bar work with the window insets.
        Spacer(modifier = Modifier.consumeWindowInsets(WindowInsets.statusBars))
        NavHost(
            navController,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            startDestination = MainDestination.Menu.fullRoute,
        ) {
            composable(
                destination = MainDestination.Menu,
            ) { _ ->
                OpeningScreenContainer(
                    onNavigateTo = { dest ->
                        navController.navigate(dest)
                    },
                )
            }

            navigation(
                route = AuthenticationNavigation.ROUTE,
                startDestination = AuthenticationNavigation.PreLogin
            ) {
                composable(
                    destination = AuthenticationNavigation.PreLogin
                ) {
                    PreLoginScreen(
                        onNavigateTo = { dest ->
                            navController.navigate(dest)
                        },
                    )
                }

                composable(
                    destination = AuthenticationNavigation.Login
                ) {
                    LoginScreen(
                        popBackStack = {
                            navController.popBackStack()
                        },
                        onNavigateAt = { dest ->
                            navController.navigate(dest)
                        }
                    )
                }

                composable(
                    destination = AuthenticationNavigation.ForgotPassword,
                ) {
                    ForgotPasswordScreen(
                        popBackStack = {
                            navController.popBackStack()
                        },
                        onNavigateAt = { dest ->
                            navController.navigate(dest)
                        }
                    )
                }

                composable(
                    destination = AuthenticationNavigation.InsertEmailToRecover(),
                    arguments = listOf(
                        navArgument("email") {
                            type = NavType.StringType
                        }
                    )
                ) { backstackEntry ->
                    val email = backstackEntry.arguments?.getString("email") ?: ""
                    InsertEmailCodeScreen(
                        email = email,
                        popBackStack = {
                            navController.popBackStack()
                        },
                    )
                }

                composable(
                    destination = AuthenticationNavigation.ReadQrCode
                ) {
                    QrCodeScreen(
                        popBackStack = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            navigation(
                route = RegistrationNavigation.ROUTE,
                startDestination = RegistrationNavigation.CreatingFamilyRouter
            ) {
                composable(
                    destination = RegistrationNavigation.CreatingFamilyRouter
                ) {
                    CreatingAccountRouterScreen(
                        onNavigateTo = {
                            navController.navigate(it)
                        },
                        popBackStack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    destination = RegistrationNavigation.InsertFamilyName
                ) {
                    InsertFamilyNameScreen(
                        popBackStack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    destination = RegistrationNavigation.InsertMemberInfo
                ) {
                    RegisterPersonalInfoScreen(
                        onNavigateAt = { dest ->
                            navController.navigate(dest)
                        },
                        popBackStack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    destination = RegistrationNavigation.EnterFamily
                ) {
                    EnterFamilyScreen(
                        popBackStack = {
                            navController.popBackStack()
                        }
                    )
                }
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
