package com.witaction.common.base

import androidx.lifecycle.liveData
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

/**
 * 数据仓库基类
 */
fun <T> fire(
    context: CoroutineContext,
    block: suspend () -> Result<T>
) = liveData(context) {
    val result = try {
        block()
    } catch (e: Exception) {
        Result.failure<T>(e)
    }
    emit(result)
}

const val SERVICE_NOT_EXIST = "服务器地址不存在"
const val CONNECT_TIME_OUT = "服务器连接超时"
const val CONNECT_FAIL = "连接失败"
const val RESPONSE_NULL = "返回数据为空"
const val NET_ERROR = "网络错误"
const val ERROR_CODE = 1

suspend fun <T> Call<BaseResp<T>>.await(): BaseResp<T> {
    return suspendCancellableCoroutine {
        enqueue(object : Callback<BaseResp<T>> {
            override fun onFailure(call: Call<BaseResp<T>>, t: Throwable) {
                t.printStackTrace()
                when (t) {
                    is UnknownHostException ->
                        it.resume(BaseResp<T>(ERROR_CODE, SERVICE_NOT_EXIST, null))
                    is HttpException ->
                        it.resume(BaseResp<T>(ERROR_CODE, CONNECT_TIME_OUT, null))
                    is ConnectException ->
                        it.resume(BaseResp<T>(ERROR_CODE, CONNECT_FAIL, null))
                    is SocketTimeoutException ->
                        it.resume(BaseResp<T>(ERROR_CODE, CONNECT_TIME_OUT, null))
                    else -> it.resume(BaseResp<T>(ERROR_CODE, NET_ERROR, null))
                }
            }

            override fun onResponse(call: Call<BaseResp<T>>, response: Response<BaseResp<T>>) {
                val body = response.body()
                if (body == null)
                    it.resume(BaseResp<T>(ERROR_CODE, RESPONSE_NULL, null))
                else
                    it.resume(body)
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
