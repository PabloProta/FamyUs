package com.famy.us.domain

import com.famy.us.domain.usecase.AuthenticateMemberUseCase
import com.famy.us.domain.usecase.IsUserLoggedUseCase
import com.famy.us.domain.usecase.IsUserRegisteredUseCase
import com.famy.us.domain.usecase.SetUserRegisteredUseCase
import org.koin.dsl.module

/**
 * Variable that store all dependencies for this module.
 */
val domainModule = module {

    factory { IsUserLoggedUseCase(get()) }
    factory { IsUserRegisteredUseCase(get()) }
    factory { SetUserRegisteredUseCase(get()) }
    factory { AuthenticateMemberUseCase(get()) }
}
