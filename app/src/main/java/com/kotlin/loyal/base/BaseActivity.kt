package com.kotlin.loyal.base

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
import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.utils.StateBarUtil
import com.kotlin.loyal.utils.StringUtil
import com.kotlin.loyal.utils.ToastUtil

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), Contact {
    private var updateReceiver: UpdateReceiver? = null
    protected var progressDialog: ProgressDialog? = null
    protected var binding: T = null!!

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
        binding = DataBindingUtil.setContentView<T>(this, layoutRes)
        StateBarUtil.setTranslucentStatus(this, isTransStatus)//沉浸式状态栏
        //ButterKnife.bind(this);
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

    @JvmOverloads fun showDialog(message: CharSequence? = null) {
        if (null != progressDialog) {
            progressDialog!!.setMessage(replaceNull(message!!))
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
        //ToastUtil.showDialog(this, replaceNull(text), finish)
    }

    fun replaceNull(t: Any): String {
        return StringUtil.replaceNull(t)
    }

    override fun onPause() {
        super.onPause()
        if (updateReceiver != null) {
            unregisterReceiver(updateReceiver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private inner class UpdateReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            val apkUrl = intent.getStringExtra("apkUrl")
            if (TextUtils.equals(action, Contact.Str.method_apkVerCheck)) {
                if (!TextUtils.isEmpty(apkUrl) && apkUrl.endsWith(".apk")) {
                    //showUpdateDialog("检测到有新的版本，是否更新?", apkUrl)
                }
            }
        }
    }

    protected fun getStrWithNull(`object`: Any): String {
        return StringUtil.replaceNull(`object`)
    }

    /**
     * 更新提示
     */
    /*public void showUpdateDialog(String content, final String apkUrl) {
        final AlertDialog myDialog = new AlertDialog.Builder(this).create();
        if (myDialog.isShowing())
            myDialog.dismiss();
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(true);
        myDialog.setCancelable(false);
        if (myDialog.getWindow() != null)
            myDialog.getWindow().setContentView(R.layout.dialog_permission);
        TextView mContent = (TextView) myDialog.getWindow().findViewById(R.id.dialog_tv_content);
        mContent.setText(getStrWithNull(content));
        Button btn_ok = (Button) myDialog.getWindow().findViewById(R.id.dialog_btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDialog.isShowing())
                    myDialog.dismiss();
                //update
                UpdateService.startActionUpdate(BaseActivity.this, Str.ACTION_DOWN, apkUrl);
            }
        });
        btn_ok.setText("立即更新");
        btn_ok.setTextSize(16);
        Button btn_cancel = (Button) myDialog.getWindow().findViewById(R.id.dialog_btn_cancel);
        btn_cancel.setText("下次再说");
        btn_cancel.setTextSize(16);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDialog.isShowing())
                    myDialog.dismiss();
            }
        });
    }*/
}
