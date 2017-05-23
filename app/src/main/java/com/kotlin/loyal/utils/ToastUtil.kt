package com.kotlin.loyal.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.IBinder
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.kotlin.loyal.R
import com.kotlin.loyal.impl.OperaOnClickListener
import com.kotlin.loyal.widget.OperateDialog

object ToastUtil {
    private var toast: Toast? = null

    fun showToast(context: Context, text: String, gravity: Int) {
        if (toast == null)
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        else {
            toast!!.setText(text)
            toast!!.setGravity(gravity, 0, 0)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        toast!!.show()
    }

    fun showToast(context: Context, text: String) {
        if (toast == null)
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        else {
            toast!!.setText(text)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        toast!!.show()
    }

    fun showToast(context: Context, text: CharSequence) {
        if (toast == null)
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        else {
            toast!!.setText(text)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        toast!!.show()
    }

    fun showToast(context: Context, resId: Int) {
        if (toast == null)
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
        else {
            toast!!.setText(resId)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        toast!!.show()
    }

    fun showDialog(context: Context, content: String, isFinish: Boolean) {
        val myDialog = AlertDialog.Builder(context).create()
        if (myDialog.isShowing)
            myDialog.dismiss()
        myDialog.show()
        myDialog.setCanceledOnTouchOutside(true)
        myDialog.setCancelable(true)
        if (myDialog.window != null)
            myDialog.window.setContentView(R.layout.dialog_permission)
        val mContent = myDialog.window.findViewById(R.id.dialog_tv_content) as TextView
        mContent.movementMethod = ScrollingMovementMethod()
        mContent.text = content
        val btn_ok = myDialog.window.findViewById(R.id.dialog_btn_ok) as Button
        val btn_cancel = myDialog.window.findViewById(R.id.dialog_btn_cancel) as Button
        val view_ok = myDialog.window.findViewById(R.id.dialog_layout_ok)
        val view_cancel = myDialog.window.findViewById(R.id.dialog_layout_cancel)

        btn_ok.setOnClickListener({
            if (myDialog != null && myDialog.isShowing)
                myDialog.dismiss()
            if (context is Activity)
                context.finish()
        })

        btn_cancel.setOnClickListener({
            if (myDialog != null && myDialog.isShowing)
                myDialog.dismiss()
        })
        view_ok.visibility = if (isFinish) View.VISIBLE else View.GONE
        view_cancel.visibility = if (isFinish) View.GONE else View.VISIBLE
        btn_cancel.text = (if (isFinish) "取消" else "确定")
    }

    fun operateDialog(context: Context, listener: OperaOnClickListener): OperateDialog {
        return OperateDialog(context, listener)
    }

    fun hideInput(context: Context, token: IBinder) {
        val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        println(im.isAcceptingText)
        println(im.isActive)
    }

    fun alwaysHideInput(context: Context, token: IBinder) {
        val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(token, 0)
    }

    fun hideInputPan(context: Activity, binder: IBinder) {
        val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (im.isActive) {
            im.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS)
        }
        context.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun showInputPan(context: Activity) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
