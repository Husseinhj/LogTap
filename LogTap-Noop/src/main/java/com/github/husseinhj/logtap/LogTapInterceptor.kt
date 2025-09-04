package com.github.husseinhj.logtap
import okhttp3.Interceptor
import okhttp3.Response

class LogTapInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response =
    chain.proceed(chain.request())
}