package com.kotlin.loyal.base;

import android.content.Context;

import com.mwm.loyal.beans.ResultBean;
import com.mwm.loyal.beans.WeatherBean;
import com.mwm.loyal.imp.ObservableServer;
import com.mwm.loyal.imp.ProgressCancelListener;
import com.mwm.loyal.imp.SubscribeListener;
import com.mwm.loyal.utils.RetrofitManage;
import com.mwm.loyal.widget.DialogHandler;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;

public class BaseProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener, ObservableServer {

    private DialogHandler.Builder builder;
    private SubscribeListener<T> subscribeListener;
    private int mWhat;
    private ObservableServer server = RetrofitManage.getInstance().getObservableServer();

    public BaseProgressSubscriber(Context context) {
        this(context, 0);
    }

    public BaseProgressSubscriber(Context context, int what) {
        this(context, what, null);
    }

    public BaseProgressSubscriber(Context context, SubscribeListener<T> listener) {
        this(context, 0, listener);
    }

    public BaseProgressSubscriber(Context context, int what, SubscribeListener<T> listener) {
        this(context, null, what, listener);
    }

    public BaseProgressSubscriber(Context context, String message, int what, SubscribeListener<T> listener) {
        subscribeListener = listener;
        this.mWhat = what;
        initDialog(context, message);
    }

    public void setWhat(int what) {
        this.mWhat = what;
    }

    private void initDialog(Context context, String message) {
        builder = new DialogHandler.Builder(context, this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(false);
    }

    public void setMessage(CharSequence sequence) {
        if (builder != null) {
            builder.setMessage(sequence);
        }
    }

    public void setCancelable(boolean flag) {
        if (builder != null)
            builder.setCancelable(flag);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        if (builder != null)
            builder.setCanceledOnTouchOutside(cancel);
    }

    private void showDialog() {
        if (builder != null) {
            builder.show();
        }
    }

    private void dismissDialog() {
        if (builder != null) {
            builder.dismiss();
            builder = null;
        }
    }

    @Override
    public void onStart() {
        showDialog();
    }

    @Override
    public void onCompleted() {
        dismissDialog();
        if (subscribeListener != null)
            subscribeListener.onCompleted(mWhat);
    }

    @Override
    public void onNext(T result) {
        if (subscribeListener != null)
            subscribeListener.onResult(mWhat, result);
    }

    @Override
    public void onError(Throwable e) {
        if (subscribeListener != null)
            subscribeListener.onError(mWhat, e);
        dismissDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!isUnsubscribed()) {
            unsubscribe();
        }
    }

    @Override
    public Observable<String> doTestLogin(@Field("account") String account, @Field("password") String password) {
        return server.doTestLogin(account, password);
    }

    @Override
    public Observable<ResultBean> doRegister(@Field("json_register") String json) {
        return server.doRegister(json);
    }

    @Override
    public Observable<ResultBean> doQueryAccount(@Field("account") String account) {
        return server.doQueryAccount(account);
    }

    @Override
    public Observable<ResultBean> doLogin(@Field("json_login") String json) {
        return server.doLogin(json);
    }

    @Override
    public Observable<ResultBean> destroyAccount(@Field("json_destroy") String json) {
        return server.destroyAccount(json);
    }

    @Override
    public Observable<ResponseBody> doShowIcon(@Field("account") String account) {
        return server.doShowIcon(account);
    }

    @Override
    public Observable<ResultBean> doUpdateAccount(@Field("json_update") String json) {
        return server.doUpdateAccount(json);
    }

    @Override
    public Observable<ResultBean> doAccountLocked(@Field("account") String account) {
        return server.doAccountLocked(account);
    }

    @Override
    public Observable<String> doUpdateIcon(@Part("description") RequestBody description, @Part MultipartBody.Part iconFile) {
        return server.doUpdateIcon(description, iconFile);
    }

    @Override
    public Observable<ResultBean> doFeedBack(@Field("json_feed") String json) {
        return server.doFeedBack(json);
    }

    @Override
    public Observable<ResultBean> deleteSelfFeed(@Field("json_delete") String json) {
        return server.deleteSelfFeed(json);
    }

    @Override
    public Observable<ResultBean> getSelfFeed(@Field("account") String account) {
        return server.getSelfFeed(account);
    }

    @Override
    public Observable<String> doUCropTest(@Field("account") String account, @Field("password") String password) {
        return server.doUCropTest(account, password);
    }

    @Override
    public Observable<ResultBean> doScan(@Field("json_scan") String json, @Field("param") String param) {
        return server.doScan(json, param);
    }

    @Override
    public Observable<ResultBean> doApkVer(@Field("apkVer") String apkVer) {
        return server.doApkVer(apkVer);
    }

    @Override
    public Observable<ResponseBody> doDownLoadApk(@Url String url) {
        return server.doDownLoadApk(url);
    }

    @Override
    public Observable<WeatherBean> getWeather(@Url String url) {
        return server.getWeather(url);
    }
}