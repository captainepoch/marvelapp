package com.adolfo.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkTools(private val context: Context) {

    fun hasInternetConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCap = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}
