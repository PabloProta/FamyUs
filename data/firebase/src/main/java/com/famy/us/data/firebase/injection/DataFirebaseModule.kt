package com.famy.us.data.firebase.injection

import com.famy.us.data.firebase.FamilyFirebaseDataSourceImpl
import com.famy.us.repository.datasource.firebase.FamilyFirebaseDataSource
import org.koin.dsl.module

/**
 * Variable that store all dependencies for this module.
 */
val dataFirebaseModule = module {

    single<FamilyFirebaseDataSource> { FamilyFirebaseDataSourceImpl() }
}
