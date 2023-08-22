package com.famy.us.core.utils.lifecycle

/**
 * Subscriber abstraction to anyone that wants to observe the app lifecycle just need inherit it.
 */
abstract class AppLifeCycleSubscriber {

    /**
     * Method that is called when the application is created.
     */
    final fun create() {
        onCreate()
    }

    /**
     * Method that is called when the application is destroyed.
     */
    final fun destroy() {
        onDestroy()
    }

    /**
     * Method that is called when the application is stopped.
     */
    final fun stop() {
        onStop()
    }

    /**
     * When the application has been created.
     */
    open fun onCreate() {}

    /**
     * When the application has been destroyed.
     */
    open fun onDestroy() {}

    /**
     * When the application has been stoped.
     */
    open fun onStop() {}
}
