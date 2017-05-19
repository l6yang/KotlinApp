package com.kotlin.loyal.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.Window;
import android.view.WindowManager;

/**
 * 多个异步任务不能共享dialog
 */
public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> implements DialogInterface.OnCancelListener {
    private ProgressDialog mDialog;

    public BaseAsyncTask(Context context) {
        if (mDialog == null)
            initDialog(context);
    }

    private void initDialog(Context context) {
        mDialog = new ProgressDialog(context);
        Window window = mDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 0.7f;// 透明度
            lp.dimAmount = 0.8f;// 黑暗度
            window.setAttributes(lp);
        }
        if (mDialog != null)
            mDialog.setOnCancelListener(this);
    }

    protected void setDialogTitle(CharSequence title) {
        if (mDialog != null)
            mDialog.setTitle(title);
    }

    protected void setDialogTitle(int resId) {
        if (mDialog != null)
            mDialog.setTitle(resId);
    }

    protected void setCancelable(boolean flag) {
        if (mDialog != null)
            mDialog.setCancelable(flag);
    }

    protected void setDialogMessage(CharSequence message) {
        if (mDialog != null)
            mDialog.setMessage(message);
    }

    protected void setCanceledOnTouchOutside(boolean cancel) {
        if (mDialog != null)
            mDialog.setCanceledOnTouchOutside(cancel);
    }

    protected void dismissDialog() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    protected void cancelDialog() {
        dismissDialog();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mDialog != null && !mDialog.isShowing())
            mDialog.show();
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        cancel(true);
    }
}
