package com.xyuan.wanandroid.listener


import androidx.lifecycle.Observer
import com.xyuan.wanandroid.data.Resource
import com.xyuan.wanandroid.util.AppLog


open abstract class BaseLiveDataObserver<R> : Observer<Resource<R>> {

    override fun onChanged(resource: Resource<R>?) {
        if (resource != null) {
            val dataResponse = resource.response
            if (dataResponse != null) {
                onSuccess(dataResponse)
            } else {
                //response为空
                if (resource.error != null && resource.status == Resource.ERROR) {
                    onError(resource.error)
                }else if (resource.status == Resource.SUCCESS){
                    onEmpty()
                }
            }
        }
    }

    abstract fun onSuccess(response: R)

    open fun onError(throwable: Throwable?) {
        AppLog.e("==BaseLiveDataObserver==onError==msg:${throwable!!.message}")
    }

    open fun onEmpty(){
        AppLog.e("==BaseLiveDataObserver==onEmpty==数据为空")
    }
}
