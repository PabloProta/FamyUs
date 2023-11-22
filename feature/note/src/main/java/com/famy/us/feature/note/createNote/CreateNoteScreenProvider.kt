package com.famy.us.feature.note.createNote

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.famy.us.core.utils.navigation.Navigator
import com.famy.us.core.utils.navigation.composable
import com.famy.us.core.utils.navigation.doAction
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.createNote.components.CreateNoteDescriptionScreen
import com.famy.us.feature.note.createNote.components.CreateNoteNameScreen
import com.famy.us.feature.note.createNote.components.DefineNoteScoreScreen
import com.famy.us.feature.note.createNote.navigation.CreateNoteNavigation
import com.famy.us.feature.note.notescreen.NoteMenuViewModel
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateNoteScreenProvider(
    onNavigate: (Navigator) -> Unit,
) {
    CreateNoteScreenContainer(onNavigate = onNavigate)
}

@Composable
internal fun CreateNoteScreenContainer(
    viewModel: NoteMenuViewModel = koinViewModel(),
    onNavigate: (Navigator) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        CreateNoteScreen(
            onFinish = {
                viewModel.perform(NoteScreenIntent.SaveTask(it))
                onNavigate(Navigator.PopBackStack)
            },
        )
    }

    BackHandler {
        onNavigate(Navigator.PopBackStack)
        viewModel.perform(NoteScreenIntent.DoBack)
    }
}

@Composable
internal fun CreateNoteScreen(onFinish: (HomeTask) -> Unit) {
    val navController = rememberNavController()
    var note by remember {
        mutableStateOf(HomeTask.Empty)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = CreateNoteNavigation.GiveName.fullRoute,
        ) {
            composable(
                destination = CreateNoteNavigation.GiveName,
            ) {
                CreateNoteNameScreen(
                    onCreateName = {
                        note = note.copy(
                            name = it,
                        )
                        navController.doAction(Navigator.NavigateTo(CreateNoteNavigation.GiveDescription))
                    },
                )
            }

            composable(
                destination = CreateNoteNavigation.GiveDescription,
                enterTransition = slideInHorizontally(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    ),
                ),
            ) {
                CreateNoteDescriptionScreen(
                    onCreateDescription = {
                        note = note.copy(
                            description = it,
                        )
                        navController.doAction(Navigator.NavigateTo(CreateNoteNavigation.GiveScore))
                    },
                )
            }

            composable(
                destination = CreateNoteNavigation.GiveScore,
                enterTransition = slideInHorizontally(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    ),
                ),
            ) {
                DefineNoteScoreScreen(
                    modifier = Modifier.fillMaxSize(),
                    onDefine = {
                        note = note.copy(
                            point = it,
                        )
                        onFinish(note)
                    },
                )
            }
        }
    }
}
