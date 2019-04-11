package com.xyuan.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xyuan.wanandroid.data.ArticleResponse
import com.xyuan.wanandroid.data.Resource
import com.xyuan.wanandroid.data.SystemBean
import com.xyuan.wanandroid.rxhttp.ApiService
import com.xyuan.wanandroid.rxhttp.RxHttpHelper
import com.xyuan.wanandroid.rxhttp.rxcommon.CommonObserver
import com.xyuan.wanandroid.rxhttp.transformer.RxTransformer
import java.util.concurrent.TimeUnit

/**
 * Created by YuanZhiQiang on  2019/4/1 0001 <br/>
 *  desc:   体系ViewModel
 */
class SystemViewModel : ViewModel(){
    private val apiService : ApiService = RxHttpHelper.getApiService()

    fun getTreeData() : MutableLiveData<Resource<ArrayList<SystemBean>>> {

        val liveData = MutableLiveData<Resource<ArrayList<SystemBean>>>()

        apiService
            .getSystemTree()
            .delay(300, TimeUnit.MILLISECONDS)
            .compose(RxTransformer.rxCompose())
            .subscribe(object : CommonObserver<ArrayList<SystemBean>>(){
                override fun onStart() {
                    liveData.postValue(Resource(Resource.START,null,null))
                }

                override fun onSuccess(response: ArrayList<SystemBean>) {
                    liveData.postValue(Resource(Resource.SUCCESS,response,null))
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    liveData.postValue(Resource(Resource.ERROR,null,e))
                }
            })

        return liveData
    }


    /**
     * 获取体系文章列表
     */
    fun getArticleData(cid : Int ,page : Int)  : MutableLiveData<Resource<ArticleResponse>>{

        val liveData = MutableLiveData<Resource<ArticleResponse>>()

        apiService
                .getSystemArticleList(page , cid)
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
}