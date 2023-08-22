package com.famy.us.core.extensions

import android.content.Context
import android.net.ConnectivityManager

/**
 * Extension to get the connectivity manager.
 */
val Context.connectivityManager
    get() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
