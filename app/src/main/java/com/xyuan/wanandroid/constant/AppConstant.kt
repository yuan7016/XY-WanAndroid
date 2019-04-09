package com.xyuan.wanandroid.constant

/**
 * Created by YuanZhiQiang on 2019/03/19 17:38
 * Desc:静态类
 */
object AppConstant {

    /**
     * BaseUrl:wanandroid网址
     */
    const val BASE_URL = "https://wanandroid.com"

    /**
     * 默认网络连接时长 10s
     */
    const val CONNECT_OUT_TIME = 10

    /**
     * 缓存文件大小 默认20MB
     */
    const val CACHE_MAX_SIZE = (20 * 1024 * 1024).toLong()

    /**
     * Cookie
     */
    const val COOKIE_KEY = "Cookie"

    /**
     * user_id
     */
    const val USER_ID_KEY = "user_id"

    /**
     * user_name
     */
    const val USER_NAME_KEY = "user_name"

    /**
     * login success
     */
    const val EVENT_LOGIN_SUCCESS = "login_success"

    /**
     * login passed 登录过期
     */
    const val EVENT_LOGIN_PASSED = "login_passed"

    /**
     * login out
     */
    const val EVENT_LOGIN_OUT = "login_out"
}