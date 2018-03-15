package com.kotlin.loyal.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import butterknife.ButterKnife
import com.kotlin.loyal.R
import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.service.UpdateService
import com.kotlin.loyal.utils.StateBarUtil
import com.kotlin.loyal.utils.StringUtil
import com.kotlin.loyal.utils.ToastUtil

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), Contact {
    private var updateReceiver: UpdateReceiver? = null
    private var progressDialog: ProgressDialog? = null
    protected var binding: T? = null

    /**
     * 绑定布局文件

     * @return LayoutRes
     */
    @get:LayoutRes
    protected abstract val layoutRes: Int

    /**
     * 初始化控件
     */
    abstract fun afterOnCreate()

    abstract val isTransStatus: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        StateBarUtil.setTranslucentStatus(this, isTransStatus)//沉浸式状态栏
        ButterKnife.bind(this)
        initDialog()
        afterOnCreate()
    }

    private fun initDialog() {
        progressDialog = ProgressDialog(this)
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

    @JvmOverloads
    fun showDialog(message: CharSequence? = null) {
        if (null != progressDialog) {
            progressDialog!!.setMessage(message?.let { replaceNull(it) })
            progressDialog!!.show()
        }
    }

    fun disMissDialog() {
        if (null != progressDialog) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState);
    }

    override fun onResume() {
        super.onResume()
        updateReceiver = UpdateReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Contact.Str.method_apkVerCheck)
        registerReceiver(updateReceiver, intentFilter)
    }

    fun showToast(text: String) {
        ToastUtil.showToast(this, text)
    }

    fun showErrorDialog(text: String, finish: Boolean) {
        StringUtil.showErrorDialog(this, replaceNull(text), finish)
    }

    fun showDialog(text: String, finish: Boolean) {
        ToastUtil.showDialog(this, replaceNull(text), finish)
    }

    fun replaceNull(sequence: CharSequence): String = StringUtil.replaceNull(sequence)

    override fun onPause() {
        super.onPause()
        if (updateReceiver != null) {
            unregisterReceiver(updateReceiver)
        }
    }

    private inner class UpdateReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            val apkUrl = intent.getStringExtra("apkUrl")
            if (TextUtils.equals(action, Contact.Str.method_apkVerCheck)) {
                if (!TextUtils.isEmpty(apkUrl) && apkUrl.endsWith(".apk")) {
                    //showUpdateDialog("检测到有新的版本，是否更新?", apkUrl);
                }
            }
        }
    }

    /**
     * 更新提示
     */
    fun showUpdateDialog(content: String, apkUrl: String) {
        val myDialog = AlertDialog.Builder(this).create()
        if (myDialog.isShowing)
            myDialog.dismiss()
        myDialog.show()
        myDialog.setCanceledOnTouchOutside(true)
        myDialog.setCancelable(false)
        if (myDialog.window != null)
            myDialog.window!!.setContentView(R.layout.dialog_permission)
        val mContent = myDialog.window!!.findViewById(R.id.dialog_tv_content) as TextView
        mContent.text = replaceNull(content)
        val btnOk = myDialog.window!!.findViewById(R.id.dialog_btn_ok) as Button
        btnOk.setOnClickListener {
            if (myDialog.isShowing)
                myDialog.dismiss()
            //update
            UpdateService.startActionUpdate(this@BaseActivity, Contact.Str.ACTION_DOWN, apkUrl)
        }
        btnOk.text = "立即更新"
        btnOk.textSize = 16f
        val btnCancel = myDialog.window!!.findViewById(R.id.dialog_btn_cancel) as Button
        btnCancel.text = "下次再说"
        btnCancel.textSize = 16f
        btnCancel.setOnClickListener {
            if (myDialog.isShowing)
                myDialog.dismiss()
        }
    }
}
