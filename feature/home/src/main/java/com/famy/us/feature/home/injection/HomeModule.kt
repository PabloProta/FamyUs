package com.famy.us.feature.home.injection

import com.famy.us.feature.home.HomeMenuLoader
import com.famy.us.feature.home.HomeViewModel
import com.famy.us.feature.home.MenusLoader
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Module for koin make the injection for the home module.
 */
val homeModule = module {
    factory { HomeMenuLoader() } bind MenusLoader::class
    viewModel { HomeViewModel(get(), get(), getAll()) }
}
