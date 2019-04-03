package com.xyuan.wanandroid.rxhttp

import com.xyuan.wanandroid.data.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by YuanZhiQiang on 2019/03/21.<p>
 * desc:API接口<p>
 * 常用注解：POST;GET<p>
 * 常用字段注解:@Field、@FieldMap、@Query、@QueryMap、@Body、@Path、@Headers、@Header</p>
 * 注意：POST请求需要加上 @FormUrlEncoded 注解 ；而GET请求不需要加
 */
interface ApiService {

    /**
     * 注册
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassowrd: String): Observable<BaseResponse<LoginResponse>>

    /**
     * 登录
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<BaseResponse<LoginResponse>>


    /**
     * 退出登录
     */
    @GET("/user/logout/json")
    fun loginOut(): Observable<BaseResponse<LoginResponse>>


    /**
     * 获取首页文章数据
     * @param page page
     */
    @GET("/article/list/{page}/json")
    fun getArticleData(@Path("page") page: Int): Observable<BaseResponse<ArticleResponse>>

    /**
     * 首页Banner
     */
    @GET("/banner/json")
    fun getBannerData(): Observable<BaseResponse<ArrayList<BannerBean>>>

    /**
     * 收藏文章
     * @param id id
     */
    @POST("/lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>

    /**
     * 取消收藏文章
     * @param id id
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>

    /**
     * 体系
     */
    @GET("/tree/json")
    fun getSystemTree(): Observable<BaseResponse<ArrayList<SystemBean>>>

    /**
     * 公众号名称
     */
    @GET("/wxarticle/chapters/json")
    fun getWechatName(): Observable<BaseResponse<ArrayList<CommonTabNameBean>>>

    /**
     * 公众号文章
     * @param id id
     * @param id page
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun getWxArticle(@Path("id") id: Int, @Path("page") page: Int): Observable<BaseResponse<ArticleResponse>>


    /**
     * 项目名称
     */
    @GET("/project/tree/json")
    fun getProjectName(): Observable<BaseResponse<ArrayList<CommonTabNameBean>>>

    /**
     * 项目列表
     * @param cid cid
     * @param page page
     */
    @GET("/project/list/{page}/json")
    fun getProjectList(@Path("page") page: Int , @Query("cid") cid: Int) : Observable<BaseResponse<ProjectResponse>>
}