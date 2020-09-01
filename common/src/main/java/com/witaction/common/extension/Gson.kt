package com.witaction.common.extension

import com.google.gson.Gson

inline fun <reified T> Gson.getObject(json: String) = fromJson(json, T::class.java)
