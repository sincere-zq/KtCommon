package com.witaction.common.utils

import java.lang.reflect.ParameterizedType

/**
 * 获取某个class的第几个泛型
 *
 * @param obj
 * @param index
 * @param <T>
 * @return
</T> */
fun <T> getClassOfT(obj: Any, index: Int): Class<T> {
    val superClass = obj.javaClass.genericSuperclass as ParameterizedType?
    return superClass!!.actualTypeArguments[index] as Class<T>
}

/**
 * 获取某个class的第几个泛型实例
 *
 * @param obj
 * @param index
 * @return
 */
fun <T> getClassOfTInstance(obj: Any, index: Int): T? {
    try {
        return getClassOfT<Any>(obj, index).newInstance() as T
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: InstantiationException) {
        e.printStackTrace()
    }
    return null
}