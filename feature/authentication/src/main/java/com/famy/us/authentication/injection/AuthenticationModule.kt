package com.famy.us.authentication.injection

import com.famy.us.authentication.AuthenticationViewModel
import com.famy.us.authentication.OneTapClient
import com.famy.us.authentication.RequestSignBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module koin for the authentication module.
 */
val authenticationModule = module {
    single { RequestSignBuilder(androidContext()) }
    single { OneTapClient(androidContext()) }
    viewModel { AuthenticationViewModel(get(), get()) }
}
