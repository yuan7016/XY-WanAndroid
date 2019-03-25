package com.xyuan.wanandroid.rxhttp


import com.xyuan.wanandroid.constant.AppConstant
import com.xyuan.wanandroid.rxhttp.interceptor.AddCookieInterceptor
import com.xyuan.wanandroid.rxhttp.interceptor.ReceiveCookieInterceptor
import com.xyuan.wanandroid.util.AppLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by YuanZhiQiang on 2019/03/22
 * Rxjava+Retrofit 网络请求
 */
object RxHttpHelper{

    private var mClient: OkHttpClient? = null

    //设置缓存目录
  //  private var mCacheFile : File = File(MainApplication.getContext().cacheDir, "RxCache")


    //日志拦截器
    private val loggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> AppLog.d("=RxHttp=", message) })
            .setLevel(HttpLoggingInterceptor.Level.BODY)


    /**
     * 获取接口服务对象
     */
    @Synchronized
    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    /**
     * 获取Retrofit
     *
     * @return
     */
    private val retrofit: Retrofit =
        Retrofit.Builder()
        .baseUrl(AppConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()


    //获取OkHttpClient
    private val okHttpClient: OkHttpClient
        get() {
            if (mClient == null) {
                mClient = OkHttpClient.Builder()
                    .connectTimeout(AppConstant.CONNECT_OUT_TIME.toLong(), TimeUnit.SECONDS)
                    .readTimeout(AppConstant.CONNECT_OUT_TIME.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(AppConstant.CONNECT_OUT_TIME.toLong(), TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(ReceiveCookieInterceptor())
                    .addInterceptor(AddCookieInterceptor())
                //  .cache(new Cache(mCacheFile, AppConstant.CACHE_MAX_SIZE))  //设置缓存
                    .build()
            }
            return mClient!!
        }

}
