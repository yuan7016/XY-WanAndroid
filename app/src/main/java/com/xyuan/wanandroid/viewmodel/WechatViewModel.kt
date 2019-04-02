package com.xyuan.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xyuan.wanandroid.data.ArticleResponse
import com.xyuan.wanandroid.data.Resource
import com.xyuan.wanandroid.data.WechatNameBean
import com.xyuan.wanandroid.rxhttp.ApiService
import com.xyuan.wanandroid.rxhttp.RxHttpHelper
import com.xyuan.wanandroid.rxhttp.rxcommon.CommonObserver
import com.xyuan.wanandroid.rxhttp.transformer.RxTransformer
import java.util.concurrent.TimeUnit

/**
 * Created by YuanZhiQiang on  2019/4/2 0002 <br/>
 *  desc: 公众号ViewModel
 */
class WechatViewModel : ViewModel(){

    private val apiService : ApiService = RxHttpHelper.getApiService()


    fun getWxName() : MutableLiveData<Resource<ArrayList<WechatNameBean>>>{
        val liveData = MutableLiveData<Resource<ArrayList<WechatNameBean>>>()

        apiService
                .getWechatName()
                .compose(RxTransformer.rxCompose())
                .subscribe(object : CommonObserver<ArrayList<WechatNameBean>>(){
                    override fun onStart() {
                        //onStart
                    }

                    override fun onSuccess(response: ArrayList<WechatNameBean>) {
                        liveData.postValue(Resource(Resource.SUCCESS,response,null))
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)

                        liveData.postValue(Resource(Resource.ERROR,null,e))
                    }

                })

        return liveData
    }


    fun getWxArticleData(id : Int ,page : Int) : MutableLiveData<Resource<ArticleResponse>>{
        val liveData = MutableLiveData<Resource<ArticleResponse>>()

        apiService
                .getWxArticle(id , page)
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