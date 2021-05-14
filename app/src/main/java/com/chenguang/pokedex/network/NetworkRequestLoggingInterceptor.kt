package com.chenguang.pokedex.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

/**
 * Interceptor implementation to print all network request information
 */
class NetworkRequestLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.i("Sending network request: $request")
        return chain.proceed(request)
    }
}
