@file:Suppress("DEPRECATION")

package com.aliceresponde.transactionboard.data.remote

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/*
   https://www.youtube.com/watch?v=IsEI1byDd4Q
 */
class NetworkConnection(
    private val context: Context,
    private var connectivityManager: ConnectivityManager
) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateConnection()
        }
    }

    override fun onActive() {
        super.onActive()
        updateConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager.registerDefaultNetworkCallback(
                connectivityManagerCallback()
            )

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> lollipopNetworkRequest()
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) connectivityManager.unregisterNetworkCallback(
            networkCallback
        )
        else context.unregisterReceiver(networkReceiver)
    }

    private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    postValue(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }
            return networkCallback
        } else {
            throw IllegalAccessError("Error")
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkRequest() {
        val requestBuilder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        connectivityManager.registerNetworkCallback(
            requestBuilder.build(),
            connectivityManagerCallback()
        )
    }

    private fun updateConnection() {
        val activeNetwork = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }

    @Throws(InterruptedException::class, IOException::class)
    fun isConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }

}