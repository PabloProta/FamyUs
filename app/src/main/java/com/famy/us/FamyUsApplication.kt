package com.famy.us

import android.app.Application
import injection.dataRepositoryModule
import injection.domainModule
import org.koin.core.context.startKoin

/**
 * Application class to init our dependencies injections using koin.
 */
class FamyUsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(dataRepositoryModule, domainModule)
        }
    }
}
