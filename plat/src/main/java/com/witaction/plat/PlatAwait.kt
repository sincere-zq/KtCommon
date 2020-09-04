package com.witaction.plat

import com.witaction.common.base.BaseResp
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.resume

const val SERVICE_NOT_EXIST = "服务器地址不存在"
const val CONNECT_TIME_OUT = "服务器连接超时"
const val CONNECT_FAIL = "连接失败"
const val RESPONSE_NULL = "返回数据为空"
const val JSON_ERROR = "解析错误"
const val NET_ERROR = "网络错误"
const val ERROR_CODE = 2
const val ERROR_CODE_404 = 404
const val SUCCESS_CODE = 200

suspend fun <T> Call<BaseResp<T>>.await(): BaseResp<T> {
    return suspendCancellableCoroutine {
        enqueue(object : Callback<BaseResp<T>> {
            override fun onFailure(call: Call<BaseResp<T>>, t: Throwable) {
                t.printStackTrace()
                when (t) {
                    is UnknownHostException ->
                        it.resume(BaseResp<T>(
                            ERROR_CODE,
                            SERVICE_NOT_EXIST, null))
                    is HttpException ->
                        it.resume(BaseResp<T>(
                            ERROR_CODE,
                            CONNECT_TIME_OUT, null))
                    is ConnectException ->
                        it.resume(BaseResp<T>(
                            ERROR_CODE,
                            CONNECT_FAIL, null))
                    is SocketTimeoutException ->
                        it.resume(BaseResp<T>(
                            ERROR_CODE,
                            CONNECT_TIME_OUT, null))
                    else -> it.resume(BaseResp<T>(
                        ERROR_CODE,
                        NET_ERROR, null))
                }
            }

            override fun onResponse(call: Call<BaseResp<T>>, response: Response<BaseResp<T>>) {
                if (response.code() == SUCCESS_CODE) {
                    val body = response.body()
                    if (body == null)
                        it.resume(BaseResp<T>(
                            ERROR_CODE,
                            RESPONSE_NULL, null))
                    else
                        it.resume(body)
                } else {
                    it.resume(BaseResp<T>(response.code(), response.message(), null))
                }
            }
        })
        it.invokeOnCancellation {
            try {
                cancel()
            } catch (e: Exception) {

            }
        }
    }
}

