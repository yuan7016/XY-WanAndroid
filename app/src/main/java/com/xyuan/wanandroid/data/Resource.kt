package com.xyuan.wanandroid.data

/**
 * Created by YuanZhiQiang on 2019/03/26 14:49
 * Desc:
 */
data class Resource<T>(val status: Int, val response: T?, val error: Throwable?) {
    companion object {

        const val START = 0
        const val SUCCESS = 1
        const val ERROR = 2

        fun <T> start(response: T?) = Resource(START, response, null)

        fun <T> success(response: T?) = Resource(SUCCESS, response, null)

        fun <T> error(response: T?,error: Throwable?) = Resource(ERROR, response, error)

    }
}