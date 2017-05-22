package com.kotlin.loyal.weight

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Handler

import com.kotlin.loyal.impl.ProgressCancelListener

/*（1）在调用前，需要先检查，因为可能为null
（2）使用b?.length的形式调用，如果b为null，返回null，否则返回b.length
（3）使用b!!.length()的形式调用，如果b为null，抛出空指针异常，否则返回b.length*/
/*另外，我们知道类型转换可能产生ClassCastException异常，例如:
var a: Long = 1
val aInt: Int? = a as Int  // java.lang.ClassCastException
那么如何避免这个异常呢？改成下面形式就可以了。

var a: Long = 1
val aInt: Int? = a as? Int
上面这种方法就是安全类型转换，如果类型转换不成功，就会返回null，而不是抛出ClassCastException异常。*/
class DialogHandler(context: Context, cancelListener: ProgressCancelListener) : Handler(), DialogInterface.OnCancelListener {
    private var progressDialog: ProgressDialog? = null
    private var context: Context? = context
    private var listener: ProgressCancelListener? = cancelListener

    private fun initDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setOnCancelListener(this)
        }
    }

    fun setDialogTitle(title: CharSequence) {
        if (progressDialog != null)
            progressDialog!!.setTitle(title)
    }

    fun setDialogTitle(resId: Int) {
        if (progressDialog != null)
            progressDialog!!.setTitle(resId)
    }

    fun setDialogMessage(message: CharSequence) {
        if (progressDialog != null)
            progressDialog!!.setMessage(message)
    }

    private fun setTitle(resId: Int) {
        if (progressDialog != null)
            progressDialog!!.setTitle(resId)
    }

    private fun setTitle(title: CharSequence) {
        if (progressDialog != null)
            progressDialog!!.setTitle(title)
    }

    private fun setCancelable(flag: Boolean) {
        if (progressDialog != null)
            progressDialog!!.setCancelable(flag)
    }

    private fun setMessage(message: CharSequence) {
        if (progressDialog != null)
            progressDialog!!.setMessage(message)
    }

    private fun setCanceledOnTouchOutside(cancel: Boolean) {
        if (progressDialog != null)
            progressDialog!!.setCanceledOnTouchOutside(cancel)
    }

    private fun dismissDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    private fun showDialog() {
        if (null != progressDialog && !progressDialog!!.isShowing) {
            progressDialog!!.show()
        }
    }

    override fun onCancel(dialogInterface: DialogInterface) {
        listener?.onCancelProgress()
    }

    class Builder(mContext: Context, listener: ProgressCancelListener) {
        val handler: DialogHandler

        init {
            handler = DialogHandler(mContext, listener)
        }

        fun setMessage(sequence: CharSequence): Builder {
            handler.setMessage(sequence)
            return this
        }

        fun setTitle(title: CharSequence): Builder {
            handler.setTitle(title)
            return this
        }

        fun setTitle(resId: Int): Builder {
            handler.setTitle(resId)
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            handler.setCancelable(cancelable)
            return this
        }

        fun setCanceledOnTouchOutside(outsideCancel: Boolean): Builder {
            handler.setCanceledOnTouchOutside(outsideCancel)
            return this
        }

        fun show() {
            handler.showDialog()
        }

        fun dismiss() {
            handler.dismissDialog()
        }
    }

    init {
        initDialog()
    }
}
