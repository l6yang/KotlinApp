package com.kotlin.loyal.base

import android.content.Context

import com.kotlin.loyal.beans.WeatherBean
import com.kotlin.loyal.impl.ObservableServer
import com.kotlin.loyal.impl.ProgressCancelListener
import com.kotlin.loyal.impl.SubscribeListener
import com.kotlin.loyal.utils.RetrofitManage
import com.kotlin.loyal.widget.DialogHandler

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.Part
import retrofit2.http.Url
import rx.Observable
import rx.Subscriber

class RxProgressSubscriber<T>(context: Context, message: String?, what: Int, showDialog: Boolean, private val subscribeListener: SubscribeListener<T>?) : Subscriber<T>(), ProgressCancelListener, ObservableServer {

    private var builder: DialogHandler.Builder? = null
    private var mWhat = 8
    private var showDialog = true
    private val server = RetrofitManage.instance.observableServer

    constructor(context: Context) : this(context, null)

    constructor(context: Context, listener: SubscribeListener<T>?) : this(context, 8, listener)

    constructor(context: Context, what: Int, listener: SubscribeListener<T>?) : this(context, what, true, listener)

    constructor(context: Context, what: Int, showDialog: Boolean, listener: SubscribeListener<T>?) : this(context, null, what, showDialog, listener)

    init {
        this.mWhat = what
        this.showDialog = showDialog
        setWhat(8)
        initDialog(context, message)
    }

    fun setWhat(what: Int) {
        this.mWhat = what
    }

    private fun initDialog(context: Context, message: String?) {
        builder = DialogHandler.Builder(context, this)
        if (null != message)
            builder!!.setMessage(message)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
    }

    fun setMessage(sequence: CharSequence) {
        if (builder != null) {
            builder!!.setMessage(sequence)
        }
    }

    fun setCancelable(flag: Boolean) {
        if (builder != null)
            builder!!.setCancelable(flag)
    }

    fun setCanceledOnTouchOutside(cancel: Boolean) {
        if (builder != null)
            builder!!.setCanceledOnTouchOutside(cancel)
    }

    private fun showDialog() {
        if (builder != null && showDialog) {
            builder!!.show()
        }
    }

    private fun dismissDialog() {
        if (builder != null) {
            builder!!.dismiss()
            builder = null
        }
    }

    override fun onStart() {
        showDialog()
    }

    override fun onCompleted() {
        dismissDialog()
        subscribeListener?.onCompleted(mWhat)
    }

    override fun onNext(result: T) {
        subscribeListener?.onResult(mWhat, result)
    }

    override fun onError(e: Throwable) {
        subscribeListener?.onError(mWhat, e)
        dismissDialog()
    }

    override fun onCancelProgress() {
        if (!isUnsubscribed) {
            unsubscribe()
        }
    }

    override fun doRegister(@Field("json_register") json: String): Observable<String> {
        return server.doRegister(json)
    }

    override fun doQueryAccount(@Field("account") account: String): Observable<String> {
        return server.doQueryAccount(account)
    }

    override fun doLogin(@Field("json_login") json: String): Observable<String> {
        return server.doLogin(json)
    }

    override fun destroyAccount(@Field("json_destroy") json: String): Observable<String> {
        return server.destroyAccount(json)
    }

    override fun downIconByAccount(@Field("account") account: String): Observable<ResponseBody> {
        return server.downIconByAccount(account)
    }

    override fun doShowIcon(@Url url: String): Observable<ResponseBody> {
        return server.doShowIcon(url)
    }

    override fun doUpdateAccount(@Field("json_update") json: String): Observable<String> {
        return server.doUpdateAccount(json)
    }

    override fun doAccountLocked(@Field("account") account: String): Observable<String> {
        return server.doAccountLocked(account)
    }

    override fun doUpdateIcon(@Part("description") description: RequestBody, @Part iconFile: MultipartBody.Part): Observable<String> {
        return server.doUpdateIcon(description, iconFile)
    }

    override fun doFeedBack(@Field("json_feed") json: String): Observable<String> {
        return server.doFeedBack(json)
    }

    override fun deleteSelfFeed(@Field("json_delete") json: String): Observable<String> {
        return server.deleteSelfFeed(json)
    }

    override fun getSelfFeed(@Field("account") account: String): Observable<String> {
        return server.getSelfFeed(account)
    }

    override fun doUCropTest(@Field("account") account: String, @Field("password") password: String): Observable<String> {
        return server.doUCropTest(account, password)
    }

    override fun doScan(@Field("json_scan") json: String, @Field("param") param: String): Observable<String> {
        return server.doScan(json, param)
    }

    override fun doApkVer(@Field("apkVer") apkVer: String): Observable<String> {
        return server.doApkVer(apkVer)
    }

    override fun doDownLoadApk(@Url url: String): Observable<ResponseBody> {
        return server.doDownLoadApk(url)
    }

    override fun getWeather(@Url url: String): Observable<WeatherBean> {
        return server.getWeather(url)
    }
}