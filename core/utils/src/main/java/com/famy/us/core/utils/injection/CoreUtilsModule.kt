package com.famy.us.core.utils.injection

import com.famy.us.core.utils.connection.ConnectionChecker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module container for core utils module.
 */
val coreUtilsModule = module {
    factory { ConnectionChecker(androidContext()) }
}
