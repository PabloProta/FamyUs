package com.famy.us

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.famy.us.core.lifecycle.AppLifeCycleSubscriber

/**
 * Class to observer the app lifecycle.
 *
 * @property subscribers the subscribers to observer the app lifeCycle.
 */
internal class AppLifeCycleObserver(
    private val subscribers: List<AppLifeCycleSubscriber>,
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        subscribers.forEach {
            it.create()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        subscribers.forEach {
            it.stop()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        subscribers.forEach {
            it.detroy()
        }
        super.onDestroy(owner)
    }
}
