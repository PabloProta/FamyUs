package com.famy.us.core.lifecycle

/**
 * Subscriber abstraction to anyone that wants to observe the app lifecycle just need inherit it.
 */
abstract class AppLifeCycleSubscriber {

    final fun create() {
        onCreate()
    }

    final fun detroy() {
        onDestroy()
    }

    final fun stop() {
        onStop()
    }

    open fun onCreate() {}

    open fun onDestroy() {}

    open fun onStop() {}
}
