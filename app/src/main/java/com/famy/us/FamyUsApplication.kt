package com.famy.us

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.famy.database.dataDatabaseModule
import com.famy.us.authentication.injection.authenticationModule
import com.famy.us.core.utils.injection.coreUtilsModule
import com.famy.us.data.firebase.injection.dataFirebaseModule
import com.famy.us.domain.domainModule
import com.famy.us.feature.home.injection.homeModule
import com.famy.us.feature.note.injection.noteModule
import com.famy.us.feature.registration.injection.registrationModule
import com.famy.us.injection.appModule
import com.famy.us.repository.dataRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

/**
 * Application class to init our dependencies injections using koin.
 */
class FamyUsApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(baseContext)
            modules(
                dataRepositoryModule,
                dataDatabaseModule,
                dataFirebaseModule,
                coreUtilsModule,
                domainModule,
                authenticationModule,
                homeModule,
                noteModule,
                registrationModule,
                appModule,
            )
        }
        observeAppLifeCycle()
    }

    private fun observeAppLifeCycle() {
        val observer: AppLifeCycleObserver by inject()
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }
}
