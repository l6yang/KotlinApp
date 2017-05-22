package com.kotlin.loyal.utils

import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.IOException
import java.io.InputStream

object IOUtil {

    /**
     * 关闭流

     * @param stream 可关闭的流
     */
    fun closeStream(stream: Closeable?) {
        try {
            stream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    fun InputStreamToByte(`is`: InputStream): ByteArray {

        val stream = ByteArrayOutputStream()
        while ((`is`.read()) != -1) {
            stream.write(`is`.read())
        }
        val byteData = stream.toByteArray()
        stream.close()
        return byteData
    }
}
