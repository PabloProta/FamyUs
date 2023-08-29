package com.famy.us.invite.injection

import com.famy.us.invite.InviteScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module container for the invite module.
 */
val inviteModule = module {
    viewModel { InviteScreenViewModel(get()) }
}
