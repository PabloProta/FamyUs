package com.famy.us.feature.note.injection

import com.famy.us.feature.home.MenusLoader
import com.famy.us.feature.note.NoteMenuLoader
import com.famy.us.feature.note.NoteMenuViewModel
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Module for koin make the injection for the note module.
 */
val noteModule = module {
    factory { NoteMenuLoader() } bind MenusLoader::class
    single { NoteMenuViewModel(get()) }
}
