package com.xyuan.wanandroid.rxhttp.exception

import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

import retrofit2.HttpException


/**
 * 错误/异常处理工具
 */
object ExceptionEngine {

    val UN_KNOWN_ERROR = 1000//未知错误
    val ANALYTIC_SERVER_DATA_ERROR = 1001//解析数据错误
    val HTTP_NET_ERROR = 1002//网络连接错误
    val TIME_OUT_ERROR = 1003//网络连接超时

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {             //HTTP错误
            ex = ApiException(e, HTTP_NET_ERROR)
            ex.msg = "网络错误，请稍后重试"  //均视为网络错误
            return ex
        } else if (e is JsonParseException || e is JSONException || e is ParseException || e is MalformedJsonException) {  //解析数据错误
            ex = ApiException(e, ANALYTIC_SERVER_DATA_ERROR)
            ex.msg = "网络数据异常，请稍后重试"
            return ex
        } else if (e is ConnectException || e is SSLHandshakeException || e is UnknownHostException) {//连接网络错误
            ex = ApiException(e, HTTP_NET_ERROR)
            ex.msg = "网络连接失败，请稍后重试"
            return ex
        } else if (e is SocketTimeoutException) {//网络超时
            ex = ApiException(e, TIME_OUT_ERROR)
            ex.msg = "网络超时，请稍后重试"
            return ex
        } else {  //未知错误
            //            ex = new ApiException(e, UN_KNOWN_ERROR);
            //            ex.setMsg("未知错误");
            ex = e as ApiException
            return ex
        }
    }

}
