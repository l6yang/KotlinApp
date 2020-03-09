package com.kotlin.loyal.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import android.telephony.TelephonyManager
import android.text.TextUtils
import java.io.File
import java.util.*

object ApkUtil {

    // 安装一个apk文件
    fun install(context: Context, uriFile: File) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val contentUri = FileProvider.getUriForFile(context, getProviderPath(context), uriFile)
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //卸载一个app
    fun uninstall(context: Context, packageName: String) {
        try {
            //通过程序的包名创建URI
            val packageURI = Uri.parse("package:" + packageName)
            //创建Intent意图
            val intent = Intent(Intent.ACTION_DELETE, packageURI)
            //执行卸载程序
            context.startActivity(intent)
        } catch (e: Exception) {
            ToastUtil.showToast(context, "卸载程序失败")
        }
    }

    //检查手机上是否安装了指定的软件
    private fun isAvailable(context: Context, packageName: String): Boolean {
        try {// 获取packageManager
            val packageManager = context.packageManager
            // 获取所有已安装程序的包信息
            val packageInfos = packageManager.getInstalledPackages(0)
            // 用于存储所有已安装程序的包名
            val packageNames = ArrayList<String>()
            // 从pinfo中将包名字逐一取出，压入pName list中
            if (packageInfos != null) {
                for (i in packageInfos.indices) {
                    val packName = packageInfos[i].packageName
                    packageNames.add(packName)
                }
            }
            // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
            return packageNames.contains(packageName)
        } catch (e: Exception) {
            return false
        }

    }

    //检查手机上是否安装了指定的软件
    fun isAvailable(context: Context, file: File): Boolean {
        try {
            return !TextUtils.isEmpty(getPackageName(context, file.absolutePath)) && isAvailable(context, getPackageName(context, file.absolutePath))
        } catch (e: Exception) {
            return false
        }

    }

    //根据文件路径获取包名
    private fun getPackageName(context: Context, filePath: String): String {
        try {
            val packageManager = context.packageManager
            val info = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES)
            if (info != null) {
                val appInfo = info.applicationInfo
                return appInfo.packageName  //得到安装包名称
            }
            return ""
        } catch (e: Exception) {
            return ""
        }

    }

    // 获得当前具体版本名
    fun getApkVersion(context: Context): String {
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            // getPackageName()是你当前类的包名,0代表是获取版本信息
            return pi.versionName
        } catch (e: Exception) {
            return "1.0.0"
        }

    }

    // 获得屏幕界面的尺寸
    fun getScreenWidth(context: Activity): Int {
        try {
            val size = Point()
            context.windowManager.defaultDisplay.getSize(size)
            return size.x
        } catch (e: Exception) {
            return 0
        }

    }

    fun getScreenHeight(context: Activity): Int {
        try {
            val size = Point()
            context.windowManager.defaultDisplay.getSize(size)
            return size.y
        } catch (e: Exception) {
            return 0
        }

    }

    fun getSimState(context: Context): Int {
        try {
            // 取得相关系统服务
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tm.simState
        } catch (e: Exception) {
            return TelephonyManager.SIM_STATE_UNKNOWN
        }

    }

    val deviceID: String
        get() = Build.MANUFACTURER + "(" + Build.MODEL + ")"

    /**
     * 获取手机状态权限之后即可读取到序列号信息
     */
    val deviceSerial: String
        @SuppressLint("HardwareIds")
        get() = Build.SERIAL

    fun getProviderPath(context: Context): String {
        return context.packageName + ".provider"
    }
}
