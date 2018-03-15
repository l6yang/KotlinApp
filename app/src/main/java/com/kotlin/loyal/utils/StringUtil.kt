package com.kotlin.loyal.utils

import android.content.Context
import android.text.TextUtils
import com.kotlin.loyal.impl.Contact
import java.util.regex.Pattern

class StringUtil : Contact {
    companion object {

        fun showErrorToast(context: Context, error: String): Boolean {
            if (showErrorToast(error)) {
                ToastUtil.showToast(context, errorException(error))
                return true
            }
            return false
        }

        fun showErrorDialog(context: Context, error: String, isFinish: Boolean) {
             ToastUtil.showDialog(context, errorException(error), isFinish)
        }

        private fun showErrorToast(error: String): Boolean {
            if (error.startsWith("java.io.IOException: Unexpected codeResponse")) {
                return true
            } else if (error.startsWith("java.net.SocketTimeoutException")) {
                return true
            } else if (error.startsWith("java.net.SocketException")) {
                return true
            } else if (error.startsWith("java.net.ConnectException")) {
                return true
            } else if (error.startsWith("java.io.EOFException")) {
                return true
            } else if (error.startsWith("java.net.UnknownHostException")) {
                return true
            } else if (error.startsWith("org.ksoap2.transport.HttpResponseException")) {
                return true
            } else if (error.startsWith("SoapFault - faultcode")) {
                return true
            } else if (TextUtils.equals(error, "err")) {
                return true
            } else if (error.startsWith("org.ksoap2.transport.HttpResponseException")) {
                return true
            }
            return false
        }

        private fun errorException(error: String): String {
            if (TextUtils.isEmpty(error)) {
                return "无数据"
            } else if (error.startsWith("java.io.IOException: Unexpected codeResponse")) {
                return "服务器响应异常"
            } else if (error.startsWith("java.net.SocketTimeoutException")) {
                return "连接服务器超时"
            } else if (error.startsWith("java.net.SocketException")) {
                return "网络服务异常"
            } else if (error.startsWith("java.net.ConnectException")) {
                return "连接服务器失败"
            } else if (error.startsWith("java.net.UnknownHostException")) {
                return "网络未连接或者当前网络地址未被识别"
            } else if (error.startsWith("org.ksoap2.transport.HttpResponseException")) {
                return "与服务器通信失败"
            } else
                return error
        }

        fun replaceNull(sequence: CharSequence): String {
            return if (TextUtils.equals(sequence.toString(), "null")) "" else sequence.toString()
        }

        fun isEmpty(str: String?): Boolean = str == null || str.trim { it <= ' ' }.isEmpty()

        fun ipValue(address: String): Boolean {
            if (isEmpty(address))
                return false
            val ipPattern = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$"
            val pattern = Pattern.compile(ipPattern)
            val matcher = pattern.matcher(address)
            return matcher.matches()
        }
    }
}