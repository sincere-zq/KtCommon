package com.witaction.plat

import android.util.Base64
import com.witaction.common.NetConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

/**
 * apiService创建
 */
class PlatServiceCreator private constructor() {
    companion object {
        const val READ_TIME_OUT: Long = 60
        const val WRITE_TIME_OUT: Long = 60
        const val CONNECT_TIME_OUT: Long = 60
        @JvmStatic
        fun getInstance() =
            SingleHolder.instance
    }

    private object SingleHolder {
        val instance = PlatServiceCreator()
    }

    val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    inline fun <reified T> create(url: String): T {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(BasicParamsInterceptor())
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
            .create(T::class.java)
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
            val request = chain.request()
            val builder = request.newBuilder()
            if ("POST".equals(chain.request().method())) {
                val body = request.body()
                var bodyString = bodyToString(body)
//                bodyString = bodyString + "&sn=" + LoginNetConfig.BASE_URL_RELEASE_APPSN
                bodyString = "$bodyString&sn=${NetConfig.BASE_URL_RELEASE_APPSN}"
                try {
                    val time: String =
                        NetConfig.BASE_URL_RELEASE_APPKEY + "_" + System.currentTimeMillis()
                    val sign = URLEncoder.encode(
                        aesEncryptString(
                            time,
                            NetConfig.SIGN_KEY
                        )?.trim()
                    )
                    bodyString = "$bodyString&sign=$sign"
                } catch (e: InvalidKeyException) {
                    e.printStackTrace()
                } catch (e: NoSuchAlgorithmException) {
                    e.printStackTrace()
                } catch (e: NoSuchPaddingException) {
                    e.printStackTrace()
                } catch (e: IllegalBlockSizeException) {
                    e.printStackTrace()
                } catch (e: InvalidAlgorithmParameterException) {
                    e.printStackTrace()
                } catch (e: BadPaddingException) {
                    e.printStackTrace()
                }
                val requestBody: RequestBody = RequestBody.create(
                    MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"),
                    bodyString
                )
                builder.post(requestBody)
            }
            val buildRequest = builder.build()
            return chain.proceed(buildRequest)
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

        private val charset = "UTF-8"

        @Throws(
            InvalidKeyException::class,
            NoSuchAlgorithmException::class,
            NoSuchPaddingException::class,
            InvalidAlgorithmParameterException::class,
            IllegalBlockSizeException::class,
            BadPaddingException::class,
            UnsupportedEncodingException::class
        )
        fun aesEncryptString(content: String, key: String): String? {
            val contentBytes = content.toByteArray(charset(charset))
            val keyBytes = key.toByteArray(charset(charset))
            val encryptedBytes = aesEncryptBytes(contentBytes, keyBytes)
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        }


        @Throws(
            NoSuchAlgorithmException::class,
            NoSuchPaddingException::class,
            InvalidKeyException::class,
            InvalidAlgorithmParameterException::class,
            IllegalBlockSizeException::class,
            BadPaddingException::class,
            UnsupportedEncodingException::class
        )
        fun aesEncryptBytes(
            contentBytes: ByteArray,
            keyBytes: ByteArray
        ): ByteArray {
            return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE)
        }

        @Throws(
            UnsupportedEncodingException::class,
            NoSuchAlgorithmException::class,
            NoSuchPaddingException::class,
            InvalidKeyException::class,
            InvalidAlgorithmParameterException::class,
            IllegalBlockSizeException::class,
            BadPaddingException::class
        )
        private fun cipherOperation(
            contentBytes: ByteArray,
            keyBytes: ByteArray,
            mode: Int
        ): ByteArray {
            val secretKey =
                SecretKeySpec(keyBytes, "AES")
            val cipher =
                Cipher.getInstance("AES/ECB/PKCS7Padding")
            cipher.init(mode, secretKey)
            return cipher.doFinal(contentBytes)
        }
    }


}
