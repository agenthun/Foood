package com.agenthun.foood

import android.support.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary

/**
 * @project Foood
 * @authors agenthun
 * @date    2018/9/8 20:52.
 */
class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}