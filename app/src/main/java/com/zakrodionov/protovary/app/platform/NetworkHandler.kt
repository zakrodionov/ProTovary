package com.zakrodionov.protovary.app.platform

import android.content.Context
import com.zakrodionov.protovary.app.ext.networkInfo


/**
 * Injectable class which returns information about the network connection state.
 */
class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected ?: false
}