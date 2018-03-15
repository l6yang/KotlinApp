package com.kotlin.loyal.handler

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.text.TextUtils
import android.view.View
import com.kotlin.loyal.R
import com.kotlin.loyal.activity.RegisterActivity
import com.kotlin.loyal.base.BaseClickHandler
import com.kotlin.loyal.base.RxProgressSubscriber
import com.kotlin.loyal.beans.LoginBean
import com.kotlin.loyal.beans.ResultBean
import com.kotlin.loyal.databinding.ActivityRegisterBinding
import com.kotlin.loyal.impl.OperaOnClickListener
import com.kotlin.loyal.impl.SubscribeListener
import com.kotlin.loyal.utils.GsonUtil
import com.kotlin.loyal.utils.RetrofitManage
import com.kotlin.loyal.utils.ToastUtil

class RegisterHandler(activity: RegisterActivity, binding: ActivityRegisterBinding?) : BaseClickHandler<ActivityRegisterBinding>(activity, binding), SubscribeListener<String>, OperaOnClickListener {
    private val fromLogin: Boolean = TextUtils.isEmpty(activity.intent.getStringExtra("account"))
    private val extra: String? = activity.intent.getStringExtra("extra")
    private val resultReg = -1
    private val resultDes = 0

    init {
        progressDialog!!.setMessage("提交信息中...")
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.account_clear -> binding!!.account.text.clear()
            R.id.password_clear -> binding!!.password.text.clear()
            R.id.repeat_clear -> binding!!.repeatMm.text.clear()
            R.id.nickname_clear -> binding!!.nickname.text.clear()
            R.id.pub_submit -> {
                ToastUtil.hideInput(activity, binding!!.account.windowToken)
                val account = binding!!.account.text.toString().trim { it <= ' ' }
                val nickname = binding!!.nickname.text.toString().trim { it <= ' ' }
                val password = binding!!.password.text.toString().trim { it <= ' ' }
                val repeat = binding!!.repeatMm.text.toString().trim { it <= ' ' }
                if (fromLogin) {
                    doRegister(account, nickname, password, repeat)
                } else {
                    if (TextUtils.isEmpty(extra))
                        doResetPassWord(account, nickname, password, repeat)
                    else
                        destroyAccount(account, nickname)
                }
            }
        }
    }

    private fun destroyAccount(account: String, password: String) {
        if (TextUtils.isEmpty(account)) {
            showToast("用户名不能为空")
            return
        }
        if (!isValue(password)) {
            showToast("密码长度少于6位")
            return
        }
        val operateDialog = ToastUtil.operateDialog(activity, this)
        operateDialog.setTitle(R.string.dialog_Title).setMessage(String.format(getString(R.string.destroy_message), account)).show()
    }

    private fun doRegister(account: String, nickname: String, password: String, repeat: String) {
        if (TextUtils.isEmpty(account)) {
            showToast("用户名不能为空")
            return
        }
        if (!isValue(password)) {
            showToast("密码长度少于6位")
            return
        }
        if (!TextUtils.equals(password, repeat)) {
            showToast("两次输入密码不一致")
            return
        }
        doAsyncTask(LoginBean(account, password, false, if (TextUtils.isEmpty(nickname)) account else nickname))
    }

    private fun doResetPassWord(account: String, nickname: String, password: String, repeat: String) {
        if (TextUtils.isEmpty(nickname)) {
            showToast("旧密码不能为空")
            return
        }
        if (!isValue(nickname) || !isValue(password)) {
            showToast("密码长度不得少于6位")
            return
        }
        if (TextUtils.equals(nickname, password)) {
            showToast("新密码和旧密码不能一致")
            return
        }
        if (!TextUtils.equals(password, repeat)) {
            showToast("两次输入密码不一致")
            return
        }
        doAsyncTask(LoginBean(account, password, nickname))
    }

    private fun doAsyncTask(loginBean: LoginBean) {
        val subscriber = RxProgressSubscriber(activity, resultReg, this@RegisterHandler)
        val observable = if (fromLogin) subscriber.doRegister(loginBean.toString()) else subscriber.doUpdateAccount(loginBean.toString())
        subscriber.setMessage("提交中...")
        RetrofitManage.rxExecuted(observable, subscriber)
    }

    private fun isValue(string: String): Boolean {
        return string.length >= 6
    }

    override fun onResult(what: Int, result: String) {
        try {
            val resultBean = GsonUtil.getBeanFromJson(result, ResultBean::class.java)
            val account = binding!!.account.text.toString().trim { it <= ' ' }
            val password = binding!!.password.text.toString().trim { it <= ' ' }
            when (what) {
                resultReg ->
                    if (resultBean.resultCode == 1) {
                        showToast(if (fromLogin) "注册成功,请牢记用户名和密码" else "修改成功，请重新登录")
                        Handler().postDelayed({
                            val intent = Intent()
                            intent.putExtra("account", account)
                            intent.putExtra("password", if (fromLogin) password else "")
                            activity.setResult(Activity.RESULT_OK, intent)
                            activity.finish()
                        }, 1000)
                    } else
                        showDialog(resultBean.resultMsg!!, false)
                resultDes ->
                    if (resultBean.resultCode == 1) {
                        showToast("账号已注销")
                        Handler().postDelayed({
                            val intent = Intent()
                            intent.putExtra("account", "")
                            activity.setResult(Activity.RESULT_OK, intent)
                            activity.finish()
                        }, 1000)
                    } else
                        showDialog(resultBean.resultMsg!!, false)
            }
        } catch (e: Exception) {
            onError(what, e.cause!!)
        }
    }

    override fun onError(what: Int, e: Throwable) {
        when (what) {
            resultReg -> showErrorDialog("解析" + (if (fromLogin) "注册" else "修改") + "信息异常", false)
            resultDes -> showErrorDialog("解析注销信息异常", false)
        }
    }

    override fun dialogCancel() {}

    override fun goNext() {
        val account = binding!!.account.text.toString().trim { it <= ' ' }
        val nickname = binding!!.nickname.text.toString().trim { it <= ' ' }
        val subscriber = RxProgressSubscriber(activity, resultDes, this)
        subscriber.setMessage("提交中...")
        RetrofitManage.rxExecuted(subscriber.destroyAccount(LoginBean(account, nickname).toString()), subscriber)
    }
}