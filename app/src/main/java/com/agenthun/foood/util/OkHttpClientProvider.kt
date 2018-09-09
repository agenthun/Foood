package com.agenthun.foood.util

import com.agenthun.foood.util.log.ALog
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @project BeautyKotlin
 * @authors agenthun
 * @date    2018/3/10 21:55.
 */
class OkHttpClientProvider {
    companion object {
        private val TAG = OkHttpClientProvider::class.java.simpleName

        fun getInstance(): OkHttpClient {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                ALog.d(TAG, it)
            })
            logger.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectionPool(ConnectionPool(5, 10, TimeUnit.SECONDS))
                    .addInterceptor(logger)
                    .addNetworkInterceptor { chain ->
                        val request = chain.request().newBuilder()
//                                .addHeader("Connection", "close")
                                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36")
                                .addHeader("Cache-Control", "public, max-age=640000")
                                .build()
                        return@addNetworkInterceptor chain.proceed(request)
                    }
                    .build()
        }
    }
}