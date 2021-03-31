package com.seven.util.ApiConfiguration

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
 // this class to use custom retrofit buildr with differnt cases
class ApiManagerDefault {
    var apiService: WebService
        private set
    var gson: Gson? = null
        get() {
            if (field == null) {
                field = GsonBuilder().setLenient().create()
            }
            return field
        }
        private set
    private var retrofit: Retrofit? = null
    private var okHttpAuthClient: OkHttpClient? = null
    private var okHttpLocalClient: OkHttpClient? = null

    constructor(authContext: Context) {
        apiService =
            getRetrofit(getHttpAuthClient(authContext)).create(
                WebService::class.java
            )
    }

    constructor(localContextActivity: Activity) {
        apiService =
            getRetrofit(getHttpLocalClient(localContextActivity)).create(
                WebService::class.java
            )
    }

    val loggingInterceptor: HttpLoggingInterceptor
        get() = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    fun getAuthInterceptor(context: Context?): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            chain.proceed(
                chain.request().newBuilder()
                    // .header("timezone", TimeZone.getDefault().id)
                    .header("Accept","application/json")
                  /*  .header("Language", /*UtilKotlin.getLocalLanguage(context!!)?:"en"*/
                        UtilKotlin.getSharedPrefs(
                            context
                        ).getString(PrefsModel.localLanguage, "en")?:"en")
                     .header("Authorization", PrefsUtil.getUserToken(context!!)?:"")*/
                     .method(original.method, original.body)
                     .build()
            )
        }
    }

    fun getLocalInterceptor(context: Context?): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            chain.proceed(
                chain.request().newBuilder()
                    .header("Accept","application/json")
                   .header("timezone", TimeZone.getDefault().id)
                 /*   .header("Language", /*UtilKotlin.getLocalLanguage(context!!)?:"en"*/
                        UtilKotlin.getSharedPrefs(
                            context!!
                        ).getString(PrefsModel.localLanguage, "en")?:"en")*/
                    //.header("Language", UtilKotlin.getLocalLanguage(context!!)?:"en")
                    .method(original.method, original.body)
                    .build()
            )
        }
    }


    fun getHttpAuthClient(context: Context): OkHttpClient {
        if (okHttpAuthClient == null) {
            okHttpAuthClient = OkHttpClient.Builder()
                .followRedirects(false)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(getAuthInterceptor(context))
              //  .addInterceptor(getCustomErrorInterceptor(context))
                .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }
        return okHttpAuthClient!!
    }

    fun getHttpLocalClient(activity: Activity): OkHttpClient {
        if (okHttpLocalClient == null) {
            okHttpLocalClient = OkHttpClient.Builder()
                .followRedirects(false)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(getLocalInterceptor(activity.applicationContext))
                //.addInterceptor(getCustomErrorInterceptor(activity.applicationContext))
                .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }
        return okHttpLocalClient!!
    }

    fun getRetrofit(client: OkHttpClient?): Retrofit {
        Log.v("error",BASE_URL)
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(/*if (BuildConfig.DEBUG)*/ BASE_URL /*else LIVE_BASE_URL*/)
                .addConverterFactory(GsonConverterFactory.create(gson!!))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client!!)
                .build()
        }
        return retrofit!!
    }

    companion object {

      val  NETWORK_TIMEOUT :Long = 30

            var BASE_URL = "http://demo1585915.mockable.io/api/"
    }

}