package com.kotlin.loyal.libs.network;

import com.kotlin.loyal.impl.Contact;
import com.kotlin.loyal.libs.network.imp.DownLoadListener;
import com.kotlin.loyal.utils.FileUtil;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DownLoadAPI implements Contact {
    private static final int DEFAULT_TIMEOUT = 15;
    private Retrofit.Builder retrofit;

    public static DownLoadAPI getInstance(DownLoadListener listener) {
        return new DownLoadAPI(listener);
    }

    private DownLoadAPI(DownLoadListener listener) {
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Str.baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    public DownloadService getDownService() {
        return retrofit.build().create(DownloadService.class);
    }

    public static void saveFile(Observable<ResponseBody> observable, final File file, Subscriber<InputStream> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        FileUtil.INSTANCE.writeFile(inputStream, file);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public interface DownloadService {

        @Streaming
        @GET
        Observable<ResponseBody> downLoad(@Url String url);
    }
}