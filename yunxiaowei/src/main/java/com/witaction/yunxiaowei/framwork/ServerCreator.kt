package com.witaction.yunxiaowei.framwork

import android.os.Build
import android.os.Handler
import android.os.Looper
import com.witaction.common.NetConfig
import com.witaction.common.base.AppManager
import com.witaction.common.extension.open
import com.witaction.common.utils.DensityUtils.screenPixel
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.ui.login.LoginActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * apiService创建
 */
class ServerCreator private constructor() {
    companion object {
        const val READ_TIME_OUT: Long = 60
        const val WRITE_TIME_OUT: Long = 60
        const val CONNECT_TIME_OUT: Long = 60
        const val NEED_RELOGIN = 402
        @JvmStatic
        fun getInstance() = SingleHolder.instance
    }

    private val handler = Handler(Looper.getMainLooper())

    private object SingleHolder {
        val instance = ServerCreator()
    }

    val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    inline fun <reified T> create(url: String): T {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(interceptor)
            .addInterceptor(TokenInterceptor())
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
            .create(T::class.java)
    }

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val request = original.newBuilder().apply {
                LocalRepository.getLoginResult()?.let {
                    header(
                        "Authorization",
                        "Bearer ${it.accessToken}"
                    )
                    header("Content-Type", "application/x-www-form-urlencoded")
                    header("X-Requested-With", "XMLHttpRequest")
                }
            }.build()
            return chain.proceed(request)
        }
    }

    class TokenInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            if (response.code() == NEED_RELOGIN) {
                getInstance().handler.post { toast(R.string.need_relogin) }
                AppManager.getInstance().getTopActivity()?.open<LoginActivity>()
            }
            return response
        }

    }

    class BasicParamsInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url()
            val url = originalHttpUrl.newBuilder().apply {
                addQueryParameter("ApiKey", NetConfig.API_KEY)
                addQueryParameter("Param", GlobalUtil.eyepetizerVersionCode.toString())
                addQueryParameter("vn", GlobalUtil.eyepetizerVersionName)
                addQueryParameter("size", screenPixel())
                addQueryParameter("deviceModel", GlobalUtil.deviceModel)
                addQueryParameter("first_channel", GlobalUtil.deviceBrand)
                addQueryParameter("last_channel", GlobalUtil.deviceBrand)
                addQueryParameter("system_version_code", "${Build.VERSION.SDK_INT}")
            }.build()
            val request = originalRequest.newBuilder().url(url)
                .method(originalRequest.method(), originalRequest.body()).build()
            return chain.proceed(request)
        }

        private fun bodyToString(request: RequestBody?): String? {
            return try {
                val buffer = Buffer()
                if (request != null) request.writeTo(buffer) else return ""
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }
    }
}
