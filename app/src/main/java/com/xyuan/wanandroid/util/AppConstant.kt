package com.xyuan.wanandroid.util

/**
 * Created by YuanZhiQiang on 2019/03/19 17:38
 * Desc:静态类
 */
class AppConstant {

    companion object {
        /**
         * BaseUrl:wanandroid网址
         */
        const val BASE_URL = "https://wanandroid.com/"

        /**
         * 默认网络连接时长 10s
         */
        const val CONNECT_OUT_TIME = 10

        /**
         * 缓存文件大小 默认20MB
         */
        const val CACHE_MAX_SIZE = (20 * 1024 * 1024).toLong()


        /**
         * 注册
         */
        const val USER_REGISTER_URL = "user/register"
        /**
         * 登录
         */
        const val USER_LOGIN_URL = "user/login"
        /**
         * 退出登录
         */
        const val USER_LOGIN_OUT_URL = "user/logout/json"

    }
}