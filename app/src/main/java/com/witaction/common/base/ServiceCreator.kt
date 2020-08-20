package com.witaction.common.base

import android.os.Build
import com.witaction.common.utils.DensityUtils.screenPixel
import com.witaction.common.utils.GlobalUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * apiService创建
 */
class ServiceCreator private constructor() {
    companion object {
        const val READ_TIME_OUT: Long = 60
        const val WRITE_TIME_OUT: Long = 60
        const val CONNECT_TIME_OUT: Long = 60
        @JvmStatic
        fun getInstance() = SingleHolder.instance
    }

    private object SingleHolder {
        val instance = ServiceCreator()
    }

    private val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    fun <T> create(url: String, serviceClass: Class<T>): T {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val request = original.newBuilder().apply {
                header("model", "Android")
                header("If-Modified-Since", "${Date()}")
                header("User-Agent", System.getProperty("http.agent") ?: "unknown")
            }.build()
            return chain.proceed(request)
        }
    }

    class BasicParamsInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url()
            val url = originalHttpUrl.newBuilder().apply {
                addQueryParameter("udid", GlobalUtil.getDeviceSerial())
                addQueryParameter("vc", GlobalUtil.eyepetizerVersionCode.toString())
                addQueryParameter("vn", GlobalUtil.eyepetizerVersionName)
                addQueryParameter("size", screenPixel())
                addQueryParameter("deviceModel", GlobalUtil.deviceModel)
                addQueryParameter("first_channel", GlobalUtil.deviceBrand)
                addQueryParameter("last_channel", GlobalUtil.deviceBrand)
                addQueryParameter("system_version_code", "${Build.VERSION.SDK_INT}")
            }.build()
            val request = originalRequest.newBuilder().url(url).method(originalRequest.method(), originalRequest.body()).build()
            return chain.proceed(request)
        }
    }
}
