package com.famy.database

import com.famy.database.datasources.HomeTaskDataSourceImpl
import com.famy.database.mapper.HomeTaskMapper
import com.famy.us.repository.datasource.HomeTaskDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Variable that store all dependencies for this module.
 */
val dataDatabaseModule = module {
    single { DatabaseProvider(androidContext()) }
    single<HomeTaskDataSource> { HomeTaskDataSourceImpl(get(), get()) }
    factory { HomeTaskMapper() }
}
