package com.xyuan.wanandroid.data

/**
 * Created by YuanZhiQiang on 2019/03/21 17:06
 * Desc:返回的数据BaseResponse
 */
data class BaseResponse<T>(var errorCode : Int,var errorMsg : String?,var data : T){


    fun isSuccess() : Boolean{
        return errorCode == 0
    }
}