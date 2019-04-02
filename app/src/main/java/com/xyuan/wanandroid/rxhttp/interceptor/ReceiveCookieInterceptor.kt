package com.xyuan.wanandroid.rxhttp.interceptor


import android.text.TextUtils
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

        val resp = chain.proceed(chain.request())
        val url = chain.request().url().toString()
        // 如果 是(登录请求 或者 注册请求) 并且 请求头包含 cookie
        if ((url.contains("user/login") || url.contains("user/register"))
            && !TextUtils.isEmpty(resp.header("Set-Cookie"))){

            // 获取 Cookie
            val cookies = resp.headers("Set-Cookie")

            if (cookies != null && cookies.size > 0) {
                val sb = StringBuilder()
                // 组装 cookie
                cookies.forEach { cookie ->
                    sb.append(cookie).append(";")
                }

                // 去掉 最后的 “ ; ”
                val cookieStr =  sb.deleteCharAt(sb.length - 1).toString()


                AppLog.e("ReceiveCookieInterceptor==Cookie===$cookieStr")
                //保存保存Cookie
                SharedPreferencesUtil.setPreferString(AppConstant.COOKIE_KEY,cookieStr)
            }
        }

        return resp
    }
}
