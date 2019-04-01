package com.xyuan.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xyuan.wanandroid.data.Resource
import com.xyuan.wanandroid.data.SystemBean
import com.xyuan.wanandroid.rxhttp.RxHttpHelper
import com.xyuan.wanandroid.rxhttp.rxcommon.CommonObserver
import com.xyuan.wanandroid.rxhttp.transformer.RxTransformer
import java.util.concurrent.TimeUnit

/**
 * Created by YuanZhiQiang on  2019/4/1 0001 <br/>
 *  desc:   体系ViewModel
 */
class SystemViewModel : ViewModel(){


    fun getTreeData() : MutableLiveData<Resource<ArrayList<SystemBean>>> {

        val liveData = MutableLiveData<Resource<ArrayList<SystemBean>>>()

        RxHttpHelper
            .getApiService()
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
}