package com.famy.us.feature.note.injection

import com.famy.us.feature.home.MenusLoader
import com.famy.us.feature.note.NoteMenuLoader
import com.famy.us.feature.note.createNote.CreateNoteScreenViewModel
import com.famy.us.feature.note.notescreen.NoteMenuViewModel
import com.famy.us.feature.note.taskContent.TaskContentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Module for koin make the injection for the note module.
 */
val noteModule = module {
    factory { NoteMenuLoader() } bind MenusLoader::class
    viewModel { NoteMenuViewModel() }
    viewModel { CreateNoteScreenViewModel(get()) }
    viewModel { TaskContentViewModel() }
}
