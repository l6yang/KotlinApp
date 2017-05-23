package com.kotlin.loyal.base

import android.app.ProgressDialog
import android.content.Intent
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StringRes
import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.utils.StringUtil
import com.kotlin.loyal.utils.ToastUtil

abstract class BaseClickHandler<V : ViewDataBinding> @JvmOverloads constructor(protected var activity: BaseActivity<*>, protected var binding: V? = null) : Contact {
    protected var progressDialog: ProgressDialog? = null

    init {
        initDialog(activity)
    }

    fun getString(@StringRes resId: Int): String {
        return activity.resources.getString(resId)
    }

    fun showToast(@StringRes resId: Int) {
        ToastUtil.showToast(activity, resId)
    }

    fun showToast(sequence: CharSequence) {
        ToastUtil.showToast(activity, replaceNull(sequence))
    }

    fun showToast(text: String) {
        ToastUtil.showToast(activity, replaceNull(text))
    }

    fun showErrorDialog(text: String, finish: Boolean) {
        StringUtil.showErrorDialog(activity, replaceNull(text), finish)
    }

    fun showDialog(text: String, finish: Boolean) {
        ToastUtil.showDialog(activity, replaceNull(text), finish)
    }

    fun startActivity(intent: Intent) {
        activity.startActivity(intent)
    }

    fun startActivityForResult(intent: Intent, reqCode: Int) {
        activity.startActivityForResult(intent, reqCode)
    }

    fun startActivityForResult(intent: Intent, reqCode: Int, bundle: Bundle) {
        activity.startActivityForResult(intent, reqCode, bundle)
    }

    fun finish() {
        activity.finish()
    }

    fun setResult(resultCode: Int) {
        activity.setResult(resultCode)
    }

    fun setResult(resultCode: Int, intent: Intent) {
        activity.setResult(resultCode, intent)
    }

    protected fun replaceNull(`object`: Any): String {
        return StringUtil.replaceNull(`object`)
    }

    private fun initDialog(baseActivity: BaseActivity<*>) {
        progressDialog = ProgressDialog(baseActivity)
        progressDialog!!.setMessage("处理中...")
        val window = progressDialog!!.window
        if (window != null) {
            val lp = window.attributes
            lp.alpha = 0.7f// 透明度
            lp.dimAmount = 0.8f// 黑暗度
            window.attributes = lp
        }
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
    }

    fun showDialog() {
        if (null != progressDialog)
            progressDialog!!.show()
    }

    fun disMissDialog() {
        if (null != progressDialog && progressDialog!!.isShowing)
            progressDialog!!.dismiss()
    }
}
