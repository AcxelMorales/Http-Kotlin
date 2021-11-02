package com.acxelmorales.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

import androidx.appcompat.app.AppCompatActivity

class Network {

    companion object {
        fun networkExist(activity: AppCompatActivity): Boolean {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(networkInfo)
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        }
    }

}
