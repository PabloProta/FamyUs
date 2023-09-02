package com.famy.us.feature.note.createNote

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.createNote.components.CreateNoteDescriptionScreen
import com.famy.us.feature.note.createNote.components.CreateNoteNameScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateNoteScreenProvider(
    onFinish: (HomeTask) -> Unit,
) {
    CreateNoteScreenContainer(onFinish = onFinish)
}

@Composable
internal fun CreateNoteScreenContainer(
    viewModel: CreateNoteScreenViewModel = koinViewModel(),
    onFinish: (HomeTask) -> Unit,
) {
    CreateNoteScreen(
        onFinish = {
            viewModel.createTask(it)
            onFinish(it)
        },
    )
}

@Composable
internal fun CreateNoteScreen(onFinish: (HomeTask) -> Unit) {
    val navController = rememberNavController()
    var note by remember {
        mutableStateOf(HomeTask.Empty)
    }
    NavHost(
        navController = navController,
        startDestination = "give_name"
    ) {

        composable(route = "give_name") {
            CreateNoteNameScreen(
                onCreateName = {
                    note = note.copy(
                        name = it
                    )
                    navController.navigate("give_description")
                },
            )
        }

        composable(route = "give_description") {
            CreateNoteDescriptionScreen(
                onCreateDescription = {
                    note = note.copy(
                        description = it
                    )
                    onFinish(note)
                },
            )
        }
    }
}


