package com.agenthun.foood.util

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @project BeautyKotlin
 * @authors agenthun
 * @date    2018/2/22 00:08.
 */

const val FOOD_PREFERENCES = "food_preferences"

private fun Context.getPreferences() =
        getSharedPreferences(FOOD_PREFERENCES, Context.MODE_PRIVATE)

fun Context.isNetConnected(): Boolean {
    val connectivity = this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivity?.activeNetworkInfo
    if (info?.isConnected == true && info.state == NetworkInfo.State.CONNECTED) {
        return true
    }
    return false
}

fun Context.isWifi(): Boolean {
    val cm = this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
}

fun Activity.openWifiSetting() {
    val intent = Intent("/")
    val cm = ComponentName("com.android.settings",
            "com.android.settings.WirelessSettings")
    intent.component = cm
    intent.action = "android.intent.action.VIEW"
    this.startActivityForResult(intent, 0)
}

fun Activity.share(text: String, title: String) {
    if (text.isNotBlank()) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.type = "text/plain"
        this.startActivity(Intent.createChooser(intent, title))
    }
}
