package com.linecy.eyepetizer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @author by linecy
 */
object NetworkUtils {

  fun isNetConnected(context: Context): Boolean {
    val connectManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo: NetworkInfo? = connectManager.activeNetworkInfo
    return if (networkInfo == null) {
      false
    } else {
      networkInfo.isAvailable && networkInfo.isConnected
    }

  }
}