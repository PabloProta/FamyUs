package com.famy.us.feature.registration.injection

import com.famy.us.feature.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module koin for registration module.
 */
val registrationModule = module {
    viewModel { RegistrationViewModel() }
}
