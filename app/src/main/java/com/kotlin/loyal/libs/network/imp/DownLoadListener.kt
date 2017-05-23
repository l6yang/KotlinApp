package com.kotlin.loyal.libs.network.imp

/**
 * 下载进度listener
 */
interface DownLoadListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}
