package com.xyuan.wanandroid.rxhttp.interceptor


import com.xyuan.wanandroid.constant.AppConstant
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.util.SharedPreferencesUtil
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
        val url = chain.request().url().toString()

        if (url.contains("user/login") || url.contains("user/register")){
            val cookies = resp.headers("Set-Cookie")
            var cookieStr = ""
            if (cookies != null && cookies.size > 0) {
                for (i in cookies.indices) {
                    cookieStr += cookies[i]
                }

                AppLog.e("==Cookie===$cookieStr")

                //保存保存Cookie
                SharedPreferencesUtil.setPreferString(AppConstant.COOKIE_KEY,cookieStr)
            }
        }

        return resp
    }
}
