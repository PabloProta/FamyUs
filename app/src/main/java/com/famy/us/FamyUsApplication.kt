package com.famy.us

import android.app.Application
import com.famy.us.domain.domainModule
import com.famy.us.injection.homeModule
import com.famy.us.repository.dataRepositoryModule
import org.koin.core.context.startKoin

/**
 * Application class to init our dependencies injections using koin.
 */
class FamyUsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                dataRepositoryModule,
                domainModule,
                homeModule,
            )
        }
    }
}
