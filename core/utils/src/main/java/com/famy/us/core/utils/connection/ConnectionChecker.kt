package com.famy.us.core.utils.connection

import android.content.Context
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import com.famy.us.core.extensions.connectivityManager

/**
 * Object to check the internet connection.
 *
 * @param context used to get the manager to handle the internet connectivity.
 */
class ConnectionChecker(context: Context) {

    private var hasInternet = false
    private val connectionListener = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            hasInternet = true
        }

        override fun onUnavailable() {
            super.onUnavailable()
            hasInternet = false
        }
    }

    init {
        context.connectivityManager.registerDefaultNetworkCallback(connectionListener)
    }

    /**
     * Method to know if this device has internet connection.
     *
     * @return [Boolean] if has internet access otherwise false.
     */
    fun hasInternetAccess(): Boolean = hasInternet
}
