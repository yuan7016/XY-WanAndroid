package com.xyuan.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xyuan.wanandroid.data.*
import com.xyuan.wanandroid.rxhttp.ApiService
import com.xyuan.wanandroid.rxhttp.RxHttpHelper
import com.xyuan.wanandroid.rxhttp.exception.ApiException
import com.xyuan.wanandroid.rxhttp.rxcommon.CommonObserver
import com.xyuan.wanandroid.rxhttp.transformer.RxTransformer
import com.xyuan.wanandroid.util.AppLog
import java.util.concurrent.TimeUnit

/**
 * Created by YuanZhiQiang on 2019/03/26 11:17
 * Desc:首页ViewModel
 */
class HomeViewModel : ViewModel() {

    private val apiService : ApiService = RxHttpHelper.getApiService()



    fun getArticleData(page:Int) : MutableLiveData<Resource<ArticleResponse>>{
        val liveData = MutableLiveData<Resource<ArticleResponse>>()
        apiService
            .getArticleData(page)
            .delay(300, TimeUnit.MILLISECONDS)
            .compose(RxTransformer.rxCompose())
            .subscribe(object : CommonObserver<ArticleResponse>(){
                override fun onStart() {
                    liveData.postValue(Resource(Resource.START,null,null))
                }

                override fun onSuccess(response: ArticleResponse) {
                    liveData.postValue(Resource(Resource.SUCCESS,response,null))
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    liveData.postValue(Resource(Resource.ERROR,null,e))
                }
            })


        return liveData
    }

    fun getBannerData() : MutableLiveData<Resource<ArrayList<BannerBean>>>{

        val liveData = MutableLiveData<Resource<ArrayList<BannerBean>>>()

        apiService
                .getBannerData()
                .compose(RxTransformer.rxCompose())
                .subscribe(object : CommonObserver<ArrayList<BannerBean>>(){
                    override fun onStart() {
                        //onStart
                    }

                    override fun onSuccess(response: ArrayList<BannerBean>) {
                        liveData.postValue(Resource(Resource.SUCCESS,response,null))
                    }

                })


        return liveData
    }

    fun collectArticle(articleID : Int , hasCollected : Boolean) : MutableLiveData<Resource<EmptyResponse>>{
        val liveData = MutableLiveData<Resource<EmptyResponse>>()

        if (hasCollected){
            apiService
                .unCollectArticle(articleID)
                .compose(RxTransformer.switchIOToMainThread())
                .subscribe(object : CommonObserver<BaseResponse<EmptyResponse>>(){
                    override fun onStart() {
                        //onStart
                    }
                    override fun onSuccess(response: BaseResponse<EmptyResponse>) {
                        if (response != null && response.isSuccess()){
                            //data 为空
                            liveData.postValue(Resource(Resource.SUCCESS,EmptyResponse(true),null))
                        }
                    }
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        liveData.postValue(Resource(Resource.ERROR,null,e))
                    }
                })
        }else{
            apiService
                .addCollectArticle(articleID)
                .compose(RxTransformer.switchIOToMainThread())
                .subscribe(object : CommonObserver<BaseResponse<EmptyResponse>>(){
                    override fun onStart() {
                        //onStart
                    }
                    override fun onSuccess(response: BaseResponse<EmptyResponse>) {
                        if (response != null && response.isSuccess()){
                            //data 为空
                            liveData.postValue(Resource(Resource.SUCCESS,EmptyResponse(true),null))
                        }else{
                            onError(ApiException(response.errorCode, response.errorMsg!!))
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)

                        liveData.postValue(Resource(Resource.ERROR,null,e))
                    }

                })
        }


        return liveData
    }
}