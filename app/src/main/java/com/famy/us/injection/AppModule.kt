package com.famy.us.injection

import com.famy.us.AppLifeCycleObserver
import org.koin.dsl.module

/**
 * app module for koin.
 */
val appModule = module {
    factory { AppLifeCycleObserver(getAll()) }
}
