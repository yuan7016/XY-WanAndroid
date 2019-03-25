package com.xyuan.wanandroid.rxhttp.interceptor

import android.content.Context
import android.text.TextUtils

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
        //        String cookieStr = ACache.get(mContext).getAsString("cookie");
        val cookieStr = "cookie"
        return if (!TextUtils.isEmpty(cookieStr)) {
            chain.proceed(chain.request().newBuilder().header("Cookie", cookieStr).build())
        } else chain.proceed(chain.request())
    }
}