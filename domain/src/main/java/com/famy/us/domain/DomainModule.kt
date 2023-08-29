package com.famy.us.domain

import com.famy.us.domain.usecase.AuthenticateMemberUseCase
import com.famy.us.domain.usecase.CreateNewFamilyUseCase
import com.famy.us.domain.usecase.GetCurrentMemberUseCase
import com.famy.us.domain.usecase.GetFamilyInviteUseCase
import com.famy.us.domain.usecase.IsUserLoggedUseCase
import org.koin.dsl.module

/**
 * Variable that store all dependencies for this module.
 */
val domainModule = module {

    factory { IsUserLoggedUseCase(get()) }
    factory { AuthenticateMemberUseCase(get()) }
    factory { CreateNewFamilyUseCase(get()) }
    factory { GetCurrentMemberUseCase(get()) }
    factory { GetFamilyInviteUseCase(get()) }
}
