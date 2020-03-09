package com.kotlin.loyal.widget;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.kotlin.loyal.impl.OperaOnClickListener;
import com.yanzhenjie.alertdialog.AlertDialog;

public class OperateDialog {

    private AlertDialog.Builder mBuilder;
    private OperaOnClickListener listener;

    public OperateDialog(@NonNull Context context, OperaOnClickListener listener) {
        mBuilder = AlertDialog.newBuilder(context)
                .setCancelable(false)
                .setTitle(com.yanzhenjie.permission.R.string.permission_title_permission_rationale)
                .setMessage(com.yanzhenjie.permission.R.string.permission_message_permission_rationale)
                .setPositiveButton(com.yanzhenjie.permission.R.string.permission_resume, mClickListener)
                .setNegativeButton(com.yanzhenjie.permission.R.string.permission_cancel, mClickListener);
        this.listener = listener;
    }

    @NonNull
    public OperateDialog setTitle(@NonNull String title) {
        mBuilder.setTitle(title);
        return this;
    }

    @NonNull
    public OperateDialog setTitle(@StringRes int title) {
        mBuilder.setTitle(title);
        return this;
    }

    @NonNull
    public OperateDialog setMessage(@NonNull String message) {
        mBuilder.setMessage(message);
        return this;
    }

    @NonNull
    public OperateDialog setMessage(@StringRes int message) {
        mBuilder.setMessage(message);
        return this;
    }

    @NonNull
    public OperateDialog setNegativeButton(@NonNull String text, @Nullable DialogInterface.OnClickListener
            negativeListener) {
        mBuilder.setNegativeButton(text, negativeListener);
        return this;
    }

    @NonNull
    public OperateDialog setNegativeButton(@StringRes int text, @Nullable DialogInterface.OnClickListener
            negativeListener) {
        mBuilder.setNegativeButton(text, negativeListener);
        return this;
    }

    @NonNull
    public OperateDialog setPositiveButton(@NonNull String text) {
        mBuilder.setPositiveButton(text, mClickListener);
        return this;
    }

    @NonNull
    public OperateDialog setPositiveButton(@StringRes int text) {
        mBuilder.setPositiveButton(text, mClickListener);
        return this;
    }

    public void show() {
        mBuilder.show();
    }

    private DialogInterface.OnClickListener mClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    listener.dialogCancel();
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    listener.goNext();
                    break;
            }
        }
    };
}
