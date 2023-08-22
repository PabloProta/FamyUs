package com.famy.us.repository

import com.famy.us.domain.repository.FamilyRepository
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.repository.mapper.FamilyMemberMapper
import com.famy.us.repository.mapper.HomeTaskMapper
import com.famy.us.repository.repository.FamilyRepositoryImpl
import com.famy.us.repository.repository.HomeTaskRepositoryImpl
import org.koin.dsl.module

/**
 * Variable that store all dependencies for this module.
 */
val dataRepositoryModule = module {
    factory { HomeTaskMapper() }
    factory { FamilyMemberMapper(get()) }

    single<FamilyRepository> { FamilyRepositoryImpl(get(), get(), get()) }
    single<HomeTaskRepository> { HomeTaskRepositoryImpl(get(), get()) }
}
