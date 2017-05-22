package com.kotlin.loyal.utils

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.kotlin.loyal.beans.ResultBean
import ikidou.reflect.TypeBuilder
import java.util.*

object GsonUtil {
    fun <T> getBeanFromJson(json: String, tClass: Class<T>): T {
        val gson = Gson()
        return gson.fromJson(json, tClass)
    }

    fun <T> fromJsonArray(json: String, clazz: Class<T>): ResultBean<List<T>> {
        val type = TypeBuilder
                .newInstance(ResultBean::class.java)
                .beginSubType(List::class.java)
                .addTypeParam(clazz)
                .endSubType()
                .build()
        return Gson().fromJson<ResultBean<List<T>>>(json, type)
    }

    fun <T> fromJsonObject(json: String, clazz: Class<T>): ResultBean<T> {
        val type = TypeBuilder
                .newInstance(ResultBean::class.java)
                .addTypeParam(clazz)
                .build()
        return Gson().fromJson<ResultBean<T>>(json, type)
    }

    fun <T> getBeanListFromJson(json: String, tClass: Class<T>): List<T> {
        val list = ArrayList<T>()
        try {
            if (TextUtils.isEmpty(json)) {
                list.clear()
                return ArrayList()
            }
            val array = JsonParser().parse(json).asJsonArray
            for (elem in array) {
                list.add(Gson().fromJson(elem, tClass) as T)
            }
        } catch (e: Exception) {
            //
        }

        return list
    }

    fun bean2Json(`object`: Any?): String {
        if (null == `object`)
            return "{}"
        return Gson().toJson(`object`)
    }

    fun list2Json(`object`: Any?): String {
        if (null == `object`)
            return "[]"
        return Gson().toJson(`object`)
    }

    fun <T> getBeanListFromRes(context: Context, resName: String, tClass: Class<T>): List<T> {
        val json = ResUtil.getStrFromRes(context, resName)
        return getBeanListFromJson(json, tClass)
    }
}