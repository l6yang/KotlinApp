package com.kotlin.loyal.handler

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.kotlin.loyal.R
import com.kotlin.loyal.activity.LoginActivity
import com.kotlin.loyal.activity.MainActivity
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
                login2MainActivity(LoginBean(account, password))
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

    private fun login2MainActivity(loginBean: LoginBean) {
        val bodySubscriber = RxProgressSubscriber<ResponseBody>(activity)
        bodySubscriber.setMessage("正在登录...")
        val observable = bodySubscriber.doShowIcon(loginBean.account.get()).subscribeOn(Schedulers.io())
        RetrofitManage.rxExecuted(observable, bodySubscriber)
        val loginSubscriber = RxProgressSubscriber(activity, -7, this)
        loginSubscriber.setMessage("正在登录...")
        val loginObservable = observable.flatMap<String> { body ->
            val iconFile = File(FileUtil.path_icon, "icon_${loginBean.account.get()}.jpg")
            FileUtil.deleteFile(iconFile)
            val save = ImageUtil.saveToFile(iconFile, body.byteStream())
            Log.d("TAG", "save：：" + save)
            if (!TextUtils.isEmpty(save))
                loginSubscriber.doLogin(loginBean.toString())
            else
                null
        }
        RetrofitManage.rxExecuted(loginObservable, loginSubscriber)
    }

    private fun toRegister(resId: Int) {
        val intent = Intent(activity, RegisterActivity::class.java)
        intent.putExtra("resId", resId)
        intent.putExtra("account", "")
        startActivityForResult(intent, Contact.Int.reqCode_register)
    }

    override fun onResult(what: Int, result: String) {
        Log.d("TAG", "::" + result)
        try {
            val resultBean = GsonUtil.getBeanFromJson(result, ResultBean::class.java)
            val account = binding!!.account.text.toString().trim { it <= ' ' }
            val password = binding!!.password.text.toString().trim { it <= ' ' }
            val loginBean = LoginBean(account, password)
            if (resultBean.resultCode == 1) {
                PreferUtil.putLoginBean(activity, loginBean)
                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("account", loginBean.account.get())
                startActivity(intent)
                finish()
            } else
                showDialog(resultBean.resultMsg!!, false)
        } catch (e: Exception) {
            Log.d("TAG", e.message)
            e.cause?.let { onError(what, it) }
        }
    }

    override fun onError(what: Int, e: Throwable) {
        disMissDialog()
        showErrorDialog(e.toString(), false)
    }

    override fun onCompleted(what: Int) {

    }
}