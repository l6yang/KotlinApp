package com.kotlin.loyal.impl

interface SubscribeListener<T> {
    fun onResult(what: Int, result: T)

    fun onError(what: Int, e: Throwable)
}