package com.famy.us

import android.app.Application
import com.famy.database.dataDatabaseModule
import com.famy.us.domain.domainModule
import com.famy.us.feature.home.injection.homeModule
import com.famy.us.feature.note.injection.noteModule
import com.famy.us.repository.dataRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class to init our dependencies injections using koin.
 */
class FamyUsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(baseContext)
            modules(
                dataRepositoryModule,
                dataDatabaseModule,
                domainModule,
                homeModule,
                noteModule,
            )
        }
    }
}
