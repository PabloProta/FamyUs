package com.famy.us.data.firebase.injection

import com.famy.us.data.firebase.datasource.FamilyFirebaseDataSourceImpl
import com.famy.us.data.firebase.mapper.FamilyMemberMapper
import com.famy.us.data.firebase.mapper.FamilyTaskMapper
import com.famy.us.repository.datasource.FamilyFirebaseDataSource
import org.koin.dsl.module

/**
 * Variable that store all dependencies for this module.
 */
val dataFirebaseModule = module {

    factory { FamilyTaskMapper() }
    factory { FamilyMemberMapper() }

    single<FamilyFirebaseDataSource> { FamilyFirebaseDataSourceImpl(get()) }
}
