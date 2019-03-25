package com.xyuan.wanandroid.rxhttp.interceptor


import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * describe:接收Cookie并保存Cookie
 */

class ReceiveCookieInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // 获取 Cookie
        val resp = chain.proceed(chain.request())
        val cookies = resp.headers("Set-Cookie")
        var cookieStr = ""
        if (cookies != null && cookies.size > 0) {
            for (i in cookies.indices) {
                cookieStr += cookies[i]
            }

            //保存Cookie
            //            ACache.get(mContext).put("cookie",cookieStr);
        }
        return resp
    }
}
