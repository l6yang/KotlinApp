package com.kotlin.loyal.service

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import com.kotlin.loyal.R
import com.kotlin.loyal.beans.ResultBean
import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.libs.network.DownLoadAPI
import com.kotlin.loyal.libs.network.DownLoadBean
import com.kotlin.loyal.libs.network.imp.DownLoadListener
import com.kotlin.loyal.utils.*
import rx.Observer
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.io.InputStream
import java.text.DecimalFormat

class UpdateService : IntentService("UpdateService"), Contact {

    private var builder: NotificationCompat.Builder? = null
    private var manager: NotificationManager? = null
    private val downloadCount = 0

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (Contact.Str.ACTION_UPDATE == action) {
                handleActionUpdate()
            } else if (Contact.Str.ACTION_DOWN == action) {
                val apkUrl = intent.getStringExtra("apkUrl")
                handleActionDownLoad(apkUrl)
            } else
                stopSelf()
        }
    }

    private fun handleActionUpdate() {
        val observable = RetrofitManage.instance.observableServer.doApkVer(ApkUtil.getApkVersion(this))
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {}

                    override fun onNext(result: String) {
                        try {
                            val resultBean = GsonUtil.getBeanFromJson(result, ResultBean::class.java)
                            if (resultBean.resultCode == 1) {
                                val url = StringUtil.replaceNull(resultBean.resultMsg)
                                //发送广播，showPopWindowForDownLoad
                                val intent = Intent()
                                intent.action = Contact.Str.method_apkVerCheck
                                intent.putExtra("apkUrl", url)
                                sendBroadcast(intent)
                            }
                        } catch (e: Exception) {
                            //
                        }

                    }
                })
    }

    private fun handleActionDownLoad(apkUrl: String) {
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        builder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.mwm)
                .setContentTitle("正在下载" + getString(R.string.app_name))
                .setContentText("已下载0%")
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.kotlin))
                .setAutoCancel(true).setOngoing(true)
        manager!!.notify(NOTIFY_TAG, 0, builder!!.build())
        val listener = object : DownLoadListener {
            override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                //不频繁发送通知，防止通知栏下拉卡顿
                val progress = (bytesRead * 100f / contentLength).toInt()
                if (downloadCount == 0 || progress > downloadCount) {
                    val download = DownLoadBean()
                    download.totalFileSize = contentLength
                    download.currentFileSize = bytesRead
                    download.progress = progress
                    showNotify(download)
                }
            }
        }
        if (!TextUtils.isEmpty(apkUrl) && apkUrl.endsWith(".apk")) {
            val file = File(FileUtil.path_apk, FileUtil.apkFileName)
            val observable = DownLoadAPI.getInstance(listener).downService.downLoad(apkUrl)
            DownLoadAPI.saveFile(observable, file, object : Subscriber<InputStream>() {
                override fun onCompleted() {
                    downloadCompleted(false, file.path)
                }

                override fun onError(e: Throwable) {
                    downloadCompleted(true, e.toString())
                }
                override fun onNext(inputStream: InputStream) {

                }
            })
        }
    }

    private fun downloadCompleted(error: Boolean, path: String) {
        //sendIntent(download);
        if (error) {
            builder!!.setContentTitle("下载异常：" + path).setProgress(100, 0, false).setOngoing(false)
        } else {
            val download = DownLoadBean()
            download.progress = 100
            manager!!.cancel(NOTIFY_TAG, 0)
            doInstallApk(path)
        }
    }

    private fun doInstallApk(path: String) {
        if (!StringUtil.showErrorToast(this@UpdateService, path)) {
            if (!TextUtils.isEmpty(path)) {
                ApkUtil.install(this@UpdateService, File(path))
            }
        }
    }

    private fun showNotify(loadBean: DownLoadBean) {
        //sendIntent(loadBean);
        val progress = loadBean.progress
        if (progress >= 100) {
            builder!!.setContentTitle("已完成").setOngoing(false)
        } else
            builder!!.setProgress(100, progress, false)
                    .setContentText(getDataSize(loadBean.currentFileSize)
                            + "/" + getDataSize(loadBean.totalFileSize))
        manager!!.notify(NOTIFY_TAG, 0, builder!!.build())
    }

    /**
     * 界面和通知栏进度同步
     */
    private fun sendIntent(download: DownLoadBean) {
        val intent = Intent("MESSAGE_PROGRESS")
        intent.putExtra("downLoad", download)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun getDataSize(var0: Long): String {
        val var2 = DecimalFormat("###.00")
        return if (var0 < 1024L)
            var0.toString() + "bytes"
        else
            if (var0 < 1048576L)
                var2.format((var0.toFloat() / 1024.0f).toDouble()) + "KB"
            else
                if (var0 < 1073741824L)
                    var2.format((var0.toFloat() / 1024.0f / 1024.0f).toDouble()) + "MB"
                else
                    if (var0 < 0L)
                        var2.format((var0.toFloat() / 1024.0f / 1024.0f / 1024.0f).toDouble()) + "GB"
                    else
                        "error"
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        manager!!.cancel(NOTIFY_TAG, 0)
    }

    companion object {
        private val NOTIFY_TAG = "Update"

        //还需传入apkUrl
        fun startActionUpdate(context: Context, action: String, apkUrl: String?) {
            val intent = Intent(context, UpdateService::class.java)
            intent.action = action
            intent.putExtra("apkUrl", apkUrl)
            context.startService(intent)
        }
    }
}