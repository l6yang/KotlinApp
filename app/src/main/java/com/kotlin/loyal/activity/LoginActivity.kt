package com.kotlin.loyal.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.kotlin.loyal.R
import com.kotlin.loyal.base.RxProgressSubscriber
import com.kotlin.loyal.beans.LoginBean
import com.kotlin.loyal.impl.SubscribeListener
import com.kotlin.loyal.impl.TextChangedListener
import com.kotlin.loyal.utils.IntentUtil
import com.kotlin.loyal.utils.RetrofitManage
import com.kotlin.loyal.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener, SubscribeListener<String> {
    override fun onClick(v: View?) {
        when (v?.id) {R.id.submit -> attemptLogin()
            R.id.account_clear -> account.text.clear()
            R.id.password_clear -> password.text.clear()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        submit.setOnClickListener(this)
        account_clear.setOnClickListener(this)
        password_clear.setOnClickListener(this)
        //kotlin 中没有new 关键字
        account.addTextChangedListener(TextChangedListener(account_clear))
        password.addTextChangedListener(TextChangedListener(password_clear))
    }

    private fun attemptLogin() {
        val account = this.account!!.text.toString()
        val password = this.password!!.text.toString()

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast(this, "请完整填写")
            return
        }
        val subscriber = RxProgressSubscriber<String>(this, true, this)
        subscriber.setMessage("登陆中...")
        val json = LoginBean(account, password).toString()
        println(json)
        RetrofitManage.rxExecuted(subscriber.doLogin(json), subscriber)
    }

    override fun onResult(what: Int, result: String) {
        print("onResult::" + result)
        try {
            intent.putExtra("accountJson", result)
            intent.setClass(this, MainActivity::class.java)
            IntentUtil.toStartActivity(this, intent)
        } catch (e: Exception) {
            print(e.localizedMessage)
            onError(what, e.cause!!);
        }
    }

    override fun onError(what: Int, e: Throwable) {
        ToastUtil.showToast(this, e.localizedMessage)
    }

    override fun onCompleted(what: Int) {
    }
}

