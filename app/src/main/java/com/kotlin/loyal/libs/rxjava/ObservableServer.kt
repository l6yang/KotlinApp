package com.kotlin.loyal.libs.rxjava

import com.kotlin.loyal.beans.WeatherBean
import com.kotlin.loyal.impl.Contact.Str.action
import com.kotlin.loyal.impl.Contact.Str.method_account_locked
import com.kotlin.loyal.impl.Contact.Str.method_apkVerCheck
import com.kotlin.loyal.impl.Contact.Str.method_deleteSelfFeed
import com.kotlin.loyal.impl.Contact.Str.method_destroyAccount
import com.kotlin.loyal.impl.Contact.Str.method_feedBack
import com.kotlin.loyal.impl.Contact.Str.method_getSelfFeed
import com.kotlin.loyal.impl.Contact.Str.method_login
import com.kotlin.loyal.impl.Contact.Str.method_queryAccount
import com.kotlin.loyal.impl.Contact.Str.method_register
import com.kotlin.loyal.impl.Contact.Str.method_scan
import com.kotlin.loyal.impl.Contact.Str.method_showIcon
import com.kotlin.loyal.impl.Contact.Str.method_ucrop_test
import com.kotlin.loyal.impl.Contact.Str.method_update
import com.kotlin.loyal.impl.Contact.Str.method_update_icon
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ObservableServer {

    @FormUrlEncoded
    @POST(action + method_register)
    fun doRegister(@Field("json_register") json: String): Observable<String>

    @FormUrlEncoded
    @POST(action + method_queryAccount)
    fun doQueryAccount(@Field("account") account: String): Observable<String>

    @FormUrlEncoded
    @POST(action + method_login)
    fun doLogin(@Field("json_login") json: String): Observable<String>

    @FormUrlEncoded
    @POST(action + method_destroyAccount)
    fun destroyAccount(@Field("json_destroy") json: String): Observable<String>

    @Streaming
    @GET
    fun doShowIcon(@Url url: String): Observable<ResponseBody>

    @Streaming
    @FormUrlEncoded
    @POST(action + method_showIcon)
    fun downIconByAccount(@Field("account") account: String): Observable<ResponseBody>

    @FormUrlEncoded
    @POST(action + method_update)
    fun doUpdateAccount(@Field("json_update") json: String): Observable<String>

    @FormUrlEncoded
    @POST(action + method_account_locked)
    fun doAccountLocked(@Field("account") account: String): Observable<String>

    @Multipart
    @POST(action + method_update_icon)
    fun doUpdateIcon(@Part("description") description: RequestBody, @Part iconFile: MultipartBody.Part): Observable<String>

    @FormUrlEncoded
    @POST(action + method_feedBack)
    fun doFeedBack(@Field("json_feed") json: String): Observable<String>

    @FormUrlEncoded
    @POST(action + method_getSelfFeed)
    fun getSelfFeed(@Field("account") account: String): Observable<String>

    @FormUrlEncoded
    @POST(action + method_deleteSelfFeed)
    fun deleteSelfFeed(@Field("json_delete") json: String): Observable<String>

    @FormUrlEncoded
    @POST(action + method_ucrop_test)
    fun doUCropTest(@Field("account") account: String, @Field("password") password: String): Observable<String>

    @FormUrlEncoded
    @POST(action + method_scan)
    fun doScan(@Field("json_scan") json: String, @Field("param") param: String): Observable<String>

    @FormUrlEncoded
    @POST(action + method_apkVerCheck)
    fun doApkVer(@Field("apkVer") apkVer: String): Observable<String>

    @Streaming
    @GET
    fun doDownLoadApk(@Url url: String): Observable<ResponseBody>

    @GET
    fun getWeather(@Url url: String): Observable<WeatherBean>
}
