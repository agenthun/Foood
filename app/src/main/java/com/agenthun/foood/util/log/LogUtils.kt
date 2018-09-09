package com.agenthun.foood.util.log

import android.os.Build
import android.util.Log
import com.agenthun.foood.BuildConfig

object LogUtils {
    private val DEFAULT_LOGGER = Logger("Beauty")

    fun v(message: String, vararg args: Any) {
        DEFAULT_LOGGER.v(message, *args)
    }

    fun d(message: String, vararg args: Any) {
        DEFAULT_LOGGER.d(message, *args)
    }

    fun i(message: String, vararg args: Any) {
        DEFAULT_LOGGER.i(message, *args)
    }

    fun w(message: String, vararg args: Any) {
        DEFAULT_LOGGER.w(message, *args)
    }

    fun e(message: String, vararg args: Any) {
        DEFAULT_LOGGER.e(message, *args)
    }

    fun e(message: String, e: Throwable) {
        DEFAULT_LOGGER.e(message, e)
    }

    fun wtf(message: String, vararg args: Any) {
        DEFAULT_LOGGER.wtf(message, *args)
    }

    fun wtf(e: Throwable) {
        DEFAULT_LOGGER.wtf(e)
    }

    class Logger(val tag: String) {
        var logTag: String = tag
        val isVerboseLoggable: Boolean
            get() = DEBUG || Log.isLoggable(logTag, Log.VERBOSE)
        val isDebugLoggable: Boolean
            get() = DEBUG || Log.isLoggable(logTag, Log.DEBUG)
        val isInfoLoggable: Boolean
            get() = DEBUG || Log.isLoggable(logTag, Log.INFO)
        val isWarnLoggable: Boolean
            get() = DEBUG || Log.isLoggable(logTag, Log.WARN)
        val isErrorLoggable: Boolean
            get() = DEBUG || Log.isLoggable(logTag, Log.ERROR)
        val isWtfLoggable: Boolean
            get() = DEBUG || Log.isLoggable(logTag, Log.ASSERT)

        fun v(message: String, vararg args: Any) {
            if (isVerboseLoggable) {
                Log.v(logTag, if (args == null || args.size == 0)
                    message
                else
                    String.format(message, args))
            }
        }

        fun d(message: String, vararg args: Any) {
            if (isDebugLoggable) {
                Log.d(logTag, if (args == null || args.size == 0)
                    message
                else
                    String.format(message, args))
            }
        }

        fun i(message: String, vararg args: Any) {
            if (isInfoLoggable) {
                Log.i(logTag, if (args == null || args.size == 0)
                    message
                else
                    String.format(message, args))
            }
        }

        fun w(message: String, vararg args: Any) {
            if (isWarnLoggable) {
                Log.w(logTag, if (args == null || args.size == 0)
                    message
                else
                    String.format(message, args))
            }
        }

        fun e(message: String, vararg args: Any) {
            if (isErrorLoggable) {
                Log.e(logTag, if (args == null || args.size == 0)
                    message
                else
                    String.format(message, args))
            }
        }

        fun e(message: String, e: Throwable) {
            if (isErrorLoggable) {
                Log.e(logTag, message, e)
            }
        }

        fun wtf(message: String, vararg args: Any) {
            if (isWtfLoggable) {
                Log.wtf(logTag, if (args == null || args.size == 0)
                    message
                else
                    String.format(message, args))
            }
        }

        fun wtf(e: Throwable) {
            if (isWtfLoggable) {
                Log.wtf(logTag, e)
            }
        }

        companion object {
            val DEBUG = (BuildConfig.DEBUG
                    || "eng" == Build.TYPE
                    || "userdebug" == Build.TYPE)
        }
    }
}