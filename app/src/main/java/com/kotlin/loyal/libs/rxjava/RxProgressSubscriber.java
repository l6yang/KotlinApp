package com.kotlin.loyal.libs.rxjava;

import android.content.Context;

import com.kotlin.loyal.beans.WeatherBean;
import com.loyal.base.rxjava.BaseRxProgressSubscriber;
import com.loyal.base.rxjava.RetrofitManage;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class RxProgressSubscriber<T> extends BaseRxProgressSubscriber<T> implements ObservableServer {
    private ObservableServer server;

    public RxProgressSubscriber(Context context, String ipAdd) {
        super(context, ipAdd);
    }

    public RxProgressSubscriber(Context context, String ipAdd, int what, boolean showDialog) {
        super(context, ipAdd, what, showDialog);
    }

    @Override
    public void createServer(RetrofitManage retrofitManage) {
        server = retrofitManage.createServer(ObservableServer.class);
    }

    @Override
    public String serverNameSpace() {
        return "mwm";
    }

    @NotNull
    @Override
    public Observable<String> doRegister(@NotNull String json) {
        return server.doRegister(json);
    }

    @NotNull
    @Override
    public Observable<String> doQueryAccount(@NotNull String account) {
        return server.doQueryAccount(account);
    }

    @NotNull
    @Override
    public Observable<String> doLogin(@NotNull String json) {
        return server.doLogin(json);
    }

    @NotNull
    @Override
    public Observable<String> destroyAccount(@NotNull String json) {
        return server.destroyAccount(json);
    }

    @NotNull
    @Override
    public Observable<ResponseBody> doShowIcon(@NotNull String url) {
        return server.doShowIcon(url);
    }

    @NotNull
    @Override
    public Observable<ResponseBody> downIconByAccount(@NotNull String account) {
        return server.downIconByAccount(account);
    }

    @NotNull
    @Override
    public Observable<String> doUpdateAccount(@NotNull String json) {
        return server.doUpdateAccount(json);
    }

    @NotNull
    @Override
    public Observable<String> doAccountLocked(@NotNull String account) {
        return server.doAccountLocked(account);
    }

    @NotNull
    @Override
    public Observable<String> doUpdateIcon(@NotNull RequestBody description, @NotNull MultipartBody.Part iconFile) {
        return server.doUpdateIcon(description, iconFile);
    }

    @NotNull
    @Override
    public Observable<String> doFeedBack(@NotNull String json) {
        return server.doFeedBack(json);
    }

    @NotNull
    @Override
    public Observable<String> getSelfFeed(@NotNull String account) {
        return server.getSelfFeed(account);
    }

    @NotNull
    @Override
    public Observable<String> deleteSelfFeed(@NotNull String json) {
        return server.deleteSelfFeed(json);
    }

    @NotNull
    @Override
    public Observable<String> doUCropTest(@NotNull String account, @NotNull String password) {
        return server.doUCropTest(account, password);
    }

    @NotNull
    @Override
    public Observable<String> doScan(@NotNull String json, @NotNull String param) {
        return server.doScan(json, param);
    }

    @NotNull
    @Override
    public Observable<String> doApkVer(@NotNull String apkVer) {
        return server.doApkVer(apkVer);
    }

    @NotNull
    @Override
    public Observable<ResponseBody> doDownLoadApk(@NotNull String url) {
        return server.doDownLoadApk(url);
    }

    @NotNull
    @Override
    public Observable<WeatherBean> getWeather(@NotNull String url) {
        return server.getWeather(url);
    }
}
