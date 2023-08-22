package com.famy.us.feature.home.injection

import com.famy.us.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module for koin make the injection for the home module.
 */
val homeModule = module {
    //  TODO - remove comments when the home screen is getting ready.
//    factory { HomeMenuLoader() } bind MenusLoader::class
    viewModel { HomeViewModel(get(), get(), getAll()) }
}
