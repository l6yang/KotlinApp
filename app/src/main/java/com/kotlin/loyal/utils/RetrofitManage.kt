package com.kotlin.loyal.utils

import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.impl.ObservableServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitManage private constructor() : Contact {

    private //设置连接超时
            //设置读取超时
            //设置写入超时
            //增加返回值为String的支持
            //增加返回值为Gson的支持(以实体类返回)
            //增加返回值为Observable<T>的支持
    val build: Retrofit.Builder
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                    .connectTimeout(25, TimeUnit.SECONDS)
                    .readTimeout(25, TimeUnit.SECONDS)
                    .writeTimeout(25, TimeUnit.SECONDS)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(Contact.Str.baseUrl)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        }

    init {
        retrofit = build
    }

    private fun getRetrofit(): Retrofit.Builder {
        if (retrofit == null)
            RetrofitManage.instance
        return retrofit as Retrofit.Builder
    }

    val observableServer: ObservableServer
        get() = getRetrofit().build().create(ObservableServer::class.java)

    companion object {
        private var mInstance: RetrofitManage? = null
        private var retrofit: Retrofit.Builder? = null

        val instance: RetrofitManage
            get() {
                if (mInstance == null) {
                    synchronized(RetrofitManage::class.java) {
                        if (mInstance == null) {
                            mInstance = RetrofitManage()
                        }
                    }
                }
                return mInstance!!
            }

        /**
         * @param call 同步执行，返回自定义类
         */
        @Throws(IOException::class)
        fun <T> doExecute(call: Call<T>): T {
            val response = call.execute()
            return response.body() as T
        }

        /**
         * @param call 同步执行，返回String
         */
        @Throws(IOException::class)
        fun doExecuteStr(call: Call<String>): String {
            val response = call.execute()
            if (response.isSuccessful)
                return response.body() as String
            else
                throw IOException("Unexpected code" + response)
        }

        fun <T> rxExecuted(observable: Observable<T>, subscriber: Subscriber<T>) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber)
        }
    }
}
