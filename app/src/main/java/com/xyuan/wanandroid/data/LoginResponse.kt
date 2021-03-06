package com.xyuan.wanandroid.data


/**
 * Created by YuanZhiQiang on 2019/03/21 17:17
 * Desc:登录、注册
 */
data class LoginResponse(var id: Int, var username: String, var password: String, var icon: String?, var type: Int){

    //LoginResponse(id=21231, username='yuan007', password='', icon=, type=0)  123456
    override fun toString(): String {
        return "LoginResponse(id=$id, username='$username', password='$password', icon=$icon, type=$type)"
    }
}