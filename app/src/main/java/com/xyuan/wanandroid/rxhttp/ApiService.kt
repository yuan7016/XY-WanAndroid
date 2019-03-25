package com.xyuan.wanandroid.rxhttp

import androidx.lifecycle.LiveData
import com.xyuan.wanandroid.constant.AppConstant
import com.xyuan.wanandroid.data.BaseResponse
import com.xyuan.wanandroid.data.LoginResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by YuanZhiQiang on 2019/03/21.<p>
 * desc:API接口<p>
 * 常用注解：POST;GET<p>
 * 常用字段注解:@Field、@FieldMap、@Query、@QueryMap、@Body、@Path、@Headers、@Header</p>
 * 注意：POST请求需要加上 @FormUrlEncoded 注解 ；而GET请求不需要加
 */
interface ApiService {


    @POST("/user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassowrd: String) : Observable<BaseResponse<LoginResponse>>


    @POST("/user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String,
                 @Field("password") password: String) : Observable<BaseResponse<LoginResponse>>



}