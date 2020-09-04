package com.witaction.common.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.getObject(json: String) = fromJson(json, T::class.java)

inline fun <reified T> Gson.getList(json: String): MutableList<T> =
    fromJson<MutableList<T>>(json, object : TypeToken<MutableList<T>>() {}.type)
