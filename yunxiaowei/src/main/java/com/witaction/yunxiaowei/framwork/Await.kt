package com.witaction.yunxiaowei.framwork

import com.google.gson.JsonSyntaxException
import com.witaction.yunxiaowei.BResp
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

suspend fun <T> Call<BResp<T>>.await(): BResp<T> {
    return suspendCancellableCoroutine {
        enqueue(object : Callback<BResp<T>> {
            override fun onFailure(call: Call<BResp<T>>, t: Throwable) {
                t.printStackTrace()
                when (t) {
                    is UnknownHostException ->
                        it.resume(
                            BResp<T>(
                                ERROR_CODE,
                                SERVICE_NOT_EXIST,
                                ERROR_CODE.toString(),
                                mutableListOf()
                            )
                        )
                    is HttpException ->
                        it.resume(
                            BResp<T>(
                                ERROR_CODE,
                                CONNECT_TIME_OUT,
                                ERROR_CODE.toString(),
                                mutableListOf()
                            )
                        )
                    is ConnectException ->
                        it.resume(
                            BResp<T>(
                                ERROR_CODE,
                                CONNECT_FAIL,
                                ERROR_CODE.toString(),
                                mutableListOf()
                            )
                        )
                    is SocketTimeoutException ->
                        it.resume(
                            BResp<T>(
                                ERROR_CODE,
                                CONNECT_TIME_OUT,
                                ERROR_CODE.toString(),
                                mutableListOf()
                            )
                        )
                    is JsonSyntaxException ->
                        it.resume(
                            BResp<T>(
                                ERROR_CODE,
                                JSON_ERROR,
                                ERROR_CODE.toString(),
                                mutableListOf()
                            )
                        )
                    else -> it.resume(
                        BResp<T>(
                            ERROR_CODE,
                            NET_ERROR,
                            ERROR_CODE.toString(),
                            mutableListOf()
                        )
                    )
                }
            }

            override fun onResponse(call: Call<BResp<T>>, response: Response<BResp<T>>) {
                if (response.code() == SUCCESS_CODE) {
                    val body = response.body()
                    if (body == null)
                        it.resume(
                            BResp<T>(
                                ERROR_CODE,
                                RESPONSE_NULL,
                                ERROR_CODE.toString(),
                                mutableListOf()
                            )
                        )
                    else
                        it.resume(body)
                } else {
                    it.resume(
                        BResp<T>(
                            response.code(),
                            response.message(),
                            response.code().toString(),
                            mutableListOf()
                        )
                    )
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

