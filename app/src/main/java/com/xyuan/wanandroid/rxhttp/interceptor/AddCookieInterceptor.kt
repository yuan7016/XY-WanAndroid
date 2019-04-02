package com.xyuan.wanandroid.rxhttp.interceptor

import android.text.TextUtils
import com.xyuan.wanandroid.constant.AppConstant
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.util.SharedPreferencesUtil
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * describe:设置cookie
 */
class AddCookieInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // 设置 Cookie
        val cookieStr = SharedPreferencesUtil.getPreferString(AppConstant.COOKIE_KEY)

        val request = chain.request()
        val newBuilder = request.newBuilder()
        val host = request.url().host()

        if (!TextUtils.isEmpty(host)){
            if (!TextUtils.isEmpty(cookieStr)){
                newBuilder.addHeader("Cookie", cookieStr)
                AppLog.w("AddCookieInterceptor==Cookie===$cookieStr")
            }
        }

        return chain.proceed(newBuilder.build())

//        return if (!TextUtils.isEmpty(cookieStr)) {
//            chain.proceed(chain.request().newBuilder().header("Cookie", cookieStr).build())
//        } else chain.proceed(chain.request())
    }
}
