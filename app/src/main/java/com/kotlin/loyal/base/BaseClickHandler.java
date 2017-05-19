package com.kotlin.loyal.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.Window;
import android.view.WindowManager;

import com.mwm.loyal.imp.Contact;
import com.mwm.loyal.utils.StringUtil;
import com.mwm.loyal.utils.ToastUtil;

public abstract class BaseClickHandler<V extends ViewDataBinding> implements Contact {
    protected ProgressDialog progressDialog;
    protected BaseActivity activity;
    protected V binding;

    public BaseClickHandler(BaseActivity baseActivity) {
        this(baseActivity, null);
    }

    public BaseClickHandler(BaseActivity baseActivity, V binding) {
        this.activity = baseActivity;
        this.binding = binding;
        initDialog(baseActivity);
    }

    public final String getString(@StringRes int resId) {
        return activity.getResources().getString(resId);
    }

    public final void showToast(@StringRes int resId) {
        ToastUtil.showToast(activity, resId);
    }

    public final void showToast(CharSequence sequence) {
        ToastUtil.showToast(activity, replaceNull(sequence));
    }

    public final void showToast(String text) {
        ToastUtil.showToast(activity, replaceNull(text));
    }

    public final void showErrorDialog(String text, boolean finish) {
        StringUtil.showErrorDialog(activity, replaceNull(text), finish);
    }

    public final void showDialog(String text, boolean finish) {
        ToastUtil.showDialog(activity, replaceNull(text), finish);
    }

    public final void startActivity(Intent intent) {
        activity.startActivity(intent);
    }

    public final void startActivityForResult(Intent intent, int reqCode) {
        activity.startActivityForResult(intent, reqCode);
    }

    public final void startActivityForResult(Intent intent, int reqCode, Bundle bundle) {
        activity.startActivityForResult(intent, reqCode, bundle);
    }

    public final void finish() {
        activity.finish();
    }

    public final void setResult(int resultCode) {
        activity.setResult(resultCode);
    }

    public final void setResult(int resultCode, Intent intent) {
        activity.setResult(resultCode, intent);
    }

    protected final String replaceNull(Object object) {
        return StringUtil.replaceNull(object);
    }

    private void initDialog(BaseActivity baseActivity) {
        progressDialog = new ProgressDialog(baseActivity);
        progressDialog.setMessage("处理中...");
        Window window = progressDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 0.7f;// 透明度
            lp.dimAmount = 0.8f;// 黑暗度
            window.setAttributes(lp);
        }
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void showDialog() {
        if (null != progressDialog)
            progressDialog.show();
    }

    public void disMissDialog() {
        if (null != progressDialog && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
