package com.xyuan.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xyuan.wanandroid.data.BaseResponse
import com.xyuan.wanandroid.data.EmptyResponse
import com.xyuan.wanandroid.data.Resource
import com.xyuan.wanandroid.rxhttp.RxHttpHelper
import com.xyuan.wanandroid.rxhttp.exception.ApiException
import com.xyuan.wanandroid.rxhttp.rxcommon.CommonObserver
import com.xyuan.wanandroid.rxhttp.transformer.RxTransformer

/**
 * Created by YuanZhiQiang on  2019/4/9 0009 <br/>
 *  desc:
 */
class SettingViewModel  : ViewModel(){

    fun outLogin() : MutableLiveData<Resource<EmptyResponse>>{
        val liveData = MutableLiveData<Resource<EmptyResponse>>()

        RxHttpHelper
                .getApiService()
                .loginOut()
                .compose(RxTransformer.switchIOToMainThread())
                .subscribe(object : CommonObserver<BaseResponse<EmptyResponse>>(){
                    override fun onStart() {
                        //onStart
                    }
                    override fun onSuccess(response: BaseResponse<EmptyResponse>) {
                        if (response != null && response.isSuccess()){
                            //data 为空
                            liveData.postValue(Resource(Resource.SUCCESS, EmptyResponse(true),null))
                        }else{
                            onError(ApiException(response.errorCode, response.errorMsg!!))
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        liveData.postValue(Resource(Resource.ERROR,null,e))
                    }

                })

        return liveData
    }
}