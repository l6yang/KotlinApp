package com.kotlin.loyal.utils

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

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

    /*  public static void showDialog(final Context context, String content, boolean isFinish) {
        final AlertDialog myDialog = new AlertDialog.Builder(context).create();
        if (myDialog.isShowing())
            myDialog.dismiss();
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(true);
        myDialog.setCancelable(true);
        if (myDialog.getWindow() != null)
            myDialog.getWindow().setContentView(R.layout.dialog_permission);
        TextView mContent = (TextView) myDialog.getWindow().findViewById(R.id.dialog_tv_content);
        mContent.setMovementMethod(new ScrollingMovementMethod());
        mContent.setText(content);
        Button btn_ok = (Button) myDialog.getWindow().findViewById(R.id.dialog_btn_ok);
        View view_ok = myDialog.getWindow().findViewById(R.id.dialog_layout_ok);
        View view_cancel = myDialog.getWindow().findViewById(R.id.dialog_layout_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDialog.isShowing())
                    myDialog.dismiss();
                ((Activity) context).finish();
            }
        });
        Button btn_cancel = (Button) myDialog.getWindow().findViewById(R.id.dialog_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDialog.isShowing())
                    myDialog.dismiss();
            }
        });
        view_ok.setVisibility(isFinish ? View.VISIBLE : View.GONE);
        view_cancel.setVisibility(isFinish ? View.GONE : View.VISIBLE);
        btn_cancel.setText(isFinish ? "取消" : "确定");
    }

    public static
    @NonNull
    OperateDialog operateDialog(@NonNull Context context, OperaOnClickListener listener) {
        return new OperateDialog(context, listener);
    }*/

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
