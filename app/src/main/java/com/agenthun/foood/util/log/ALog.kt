package com.agenthun.foood.util.log

import android.util.Log
import org.jetbrains.annotations.NonNls


/**
 * @project BeautyKotlin
 * @authors agenthun
 * @date    2018/2/22 00:49.
 */
class ALog {
    companion object {
        private val LOGGER = LogUtils.Logger("ALog")

        fun v(tag: String, @NonNls message: String, vararg args: Any) {
//            Timber.tag(tag)
//            Timber.v(message, args)
        }

        fun v(tag: String, t: Throwable, @NonNls message: String, vararg args: Any) {
//            Timber.tag(tag)
//            Timber.v(t, message, args)
        }

        fun v(tag: String, t: Throwable) {
//            Timber.tag(tag)
//            Timber.v(tag, t)
        }

        fun d(tag: String, @NonNls message: String, vararg args: Any) {
//            LOGGER.logTag = tag
//            LOGGER.d(message, args)
//            Timber.tag(tag)
//            Timber.d(message, args)
            Log.d(tag, message)
        }

        fun d(tag: String, t: Throwable, @NonNls message: String, vararg args: Any) {
//            Timber.tag(tag)
//            Timber.d(t, message, args)
        }

        fun d(tag: String, t: Throwable) {
//            Timber.tag(tag)
//            Timber.d(t)
        }

        fun i(tag: String, @NonNls message: String, vararg args: Any) {
//            Timber.tag(tag)
//            Timber.i(message, args)
            Log.i(tag, message)
        }

        fun i(tag: String, t: Throwable, @NonNls message: String, vararg args: Any) {
//            Timber.tag(tag)
//            Timber.i(t, message, args)
        }

        fun i(tag: String, t: Throwable) {
//            Timber.tag(tag)
//            Timber.i(t)
        }

        fun w(tag: String, @NonNls message: String, vararg args: Any) {
//            Timber.tag(tag)
//            Timber.w(message, args)
        }

        fun w(tag: String, t: Throwable, @NonNls message: String, vararg args: Any) {
//            Timber.tag(tag)
//            Timber.w(t, message, args)
        }

        fun w(tag: String, t: Throwable) {
//            Timber.tag(tag)
//            Timber.w(t)
        }

        fun e(tag: String, @NonNls message: String, vararg args: Any) {
//            Timber.tag(tag)
//            Timber.e(message, args)
            Log.e(tag, message)
        }

        fun e(tag: String, t: Throwable, @NonNls message: String, vararg args: Any) {
//            Timber.tag(tag)
//            Timber.e(t, message, args)
        }

        fun e(tag: String, t: Throwable) {
//            Timber.tag(tag)
//            Timber.e(t)
        }
    }
}