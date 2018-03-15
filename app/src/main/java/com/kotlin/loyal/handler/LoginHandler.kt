package com.kotlin.loyal.handler

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.kotlin.loyal.R
import com.kotlin.loyal.activity.LoginActivity
import com.kotlin.loyal.activity.RegisterActivity
import com.kotlin.loyal.base.BaseClickHandler
import com.kotlin.loyal.base.RxProgressSubscriber
import com.kotlin.loyal.beans.LoginBean
import com.kotlin.loyal.beans.ResultBean
import com.kotlin.loyal.databinding.ActivityLoginBinding
import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.impl.SubscribeListener
import com.kotlin.loyal.utils.*
import okhttp3.ResponseBody
import rx.schedulers.Schedulers
import java.io.File

class LoginHandler(activity: LoginActivity, binding: ActivityLoginBinding?) : BaseClickHandler<ActivityLoginBinding>(activity, binding), SubscribeListener<String> {

    fun onClick(view: View) {
        when (view.id) {
            R.id.pub_submit -> {
                val account = binding!!.account.text.toString().trim { it <= ' ' }
                val password = binding!!.password.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(account)) {
                    showToast("用户名不能为空")
                    return
                }
                if (TextUtils.isEmpty(password) || password.length < 6) {
                    showToast("密码长度格式错误")
                    return
                }
                /*val loginSubscriber = RxProgressSubscriber(activity, -7, this)
                loginSubscriber.setMessage("正在登录...")
                RetrofitManage.rxExecuted(loginSubscriber.doLogin(LoginBean(account, password).toString()), loginSubscriber)
           */downLoadUserIcon(LoginBean(account, password))
            }
            R.id.account_clear -> binding!!.account.text.clear()
            R.id.password_clear -> binding!!.password.text.clear()
            R.id.server_clear -> binding!!.server.text.clear()
            R.id.mm_forget -> {
                //val intent = Intent(activity, ForgetActivity::class.java)
                //startActivity(intent)
            }
            R.id.register -> toRegister(R.id.pub_submit)
        }
        ToastUtil.hideInput(activity, binding!!.account.windowToken)
    }

    private fun downLoadUserIcon(loginBean: LoginBean) {
        /*解决方式有两种*/
        /*①、val server = RetrofitManage.instance.observableServer
        val url = Contact.Str.getIconUrl(loginBean.account.get())
        server.doShowIcon(url).subscribeOn(Schedulers.newThread())
                .map { byteArray ->
                    val fileName = "icon_${loginBean.account.get()}.jpg"
                    println(fileName)
                    val iconFile = File(FileUtil.path_icon, fileName)
                    FileUtil.deleteFile(iconFile)
                    println(byteArray.contentLength())
                    ImageUtil.saveToFile(iconFile, byteArray.bytes())
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<String>() {
                    override fun onStart() {
                        super.onStart()
                        println("onStart")
                    }

                    override fun onCompleted() {
                        println("onCompleted")
                    }

                    override fun onError(e: Throwable) {
                        println("onError")
                        println(e.message)
                    }

                    override fun onNext(s: String) {
                        println("onNext" + s)
                    }
                })*/
        /*②、可以使用map也可以使用flatMap
        * .subscribeOn(Schedulers.io())可以使用①
        * .observeOn可以忽略*/
        val bodySubscriber = RxProgressSubscriber<ResponseBody>(activity)
        bodySubscriber.setWhat(-5)
        bodySubscriber.setMessage("正在登录...")
        val observable = bodySubscriber.downIconByAccount(loginBean.account.get()).subscribeOn(Schedulers.io())
        RetrofitManage.rxExecuted(observable, bodySubscriber)
        val loginSubscriber = RxProgressSubscriber(activity, -7, this)
        loginSubscriber.setMessage("正在登录...")
        val loginObservable = observable.flatMap({ body ->
            val iconFile = File(FileUtil.path_icon, "icon_${loginBean.account.get()}.jpg")
            FileUtil.deleteFile(iconFile)
            val save = ImageUtil.saveToFile(iconFile, body.bytes())
            if (!TextUtils.isEmpty(save))
                loginSubscriber.doLogin(loginBean.toString())
            else
                null
        })//.observeOn(AndroidSchedulers.mainThread())
        RetrofitManage.rxExecuted(loginObservable, loginSubscriber)
    }

    private fun toRegister(resId: Int) {
        val intent = Intent(activity, RegisterActivity::class.java)
        intent.putExtra("resId", resId)
        intent.putExtra("account", "")
        startActivityForResult(intent, Contact.Int.reqCode_register)
    }

    override fun onResult(what: Int, result: String) {
        try {
            val resultBean = GsonUtil.getBeanFromJson(result, ResultBean::class.java)
            val account = binding!!.account.text.toString().trim { it <= ' ' }
            val password = binding!!.password.text.toString().trim { it <= ' ' }
            val loginBean = LoginBean(account, password)
            if (resultBean.resultCode == 1) {
                //downLoadUserIcon(loginBean)
            } else
                showDialog(resultBean.resultMsg!!, false)
        } catch (e: Exception) {
            e.cause?.let { onError(what, it) }
        }
    }

    override fun onError(what: Int, e: Throwable) {
        disMissDialog()
        showErrorDialog(e.toString(), false)
    }
}