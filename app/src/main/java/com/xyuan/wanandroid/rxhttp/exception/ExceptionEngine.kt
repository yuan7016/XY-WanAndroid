package com.xyuan.wanandroid.rxhttp.exception

import com.blankj.rxbus.RxBus
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.xyuan.wanandroid.constant.AppConstant
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.util.SharedPreferencesUtil
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

    const val UN_KNOWN_ERROR = 1000//未知错误
    const val LOGIN_ERROR = -1000//登录超时
    const val ANALYTIC_SERVER_DATA_ERROR = 1001//解析数据错误
    const val HTTP_NET_ERROR = 1002//网络连接错误
    const val TIME_OUT_ERROR = 1003//网络连接超时

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {             //HTTP错误
            ex = ApiException(e, HTTP_NET_ERROR)
            ex.msg = "网络错误，请稍后重试"  //均视为网络错误
            return ex
        } else if (e is JsonParseException || e is JSONException || e is ParseException || e is MalformedJsonException) {  //解析数据错误
            ex = ApiException(e, ANALYTIC_SERVER_DATA_ERROR)
            ex.msg = "数据解析异常"
            return ex
        } else if (e is ConnectException || e is SSLHandshakeException || e is UnknownHostException) {//连接网络错误
            ex = ApiException(e, HTTP_NET_ERROR)
            ex.msg = "网络连接失败，请稍后重试"
            return ex
        } else if (e is SocketTimeoutException) {//网络超时
            ex = ApiException(e, TIME_OUT_ERROR)
            ex.msg = "网络超时，请稍后重试"
            return ex
        } else if (e is ApiException) {//登录超时
            AppLog.e("==handleException=登录超时===code=${e.code}----message=${e.msg}")
            if (e.code == LOGIN_ERROR){
                SharedPreferencesUtil.clearAllSPData()
                RxBus.getDefault().post(AppConstant.EVENT_LOGIN_PASSED)
            }

            return e
        } else {  //未知错误
            AppLog.e("==handleException=未知错误===")
            ex = ApiException(e, UN_KNOWN_ERROR)
            ex.msg = e.message

            return ex
        }
    }

}
