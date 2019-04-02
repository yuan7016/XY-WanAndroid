package com.xyuan.wanandroid.rxhttp.rxcommon

import com.hjq.toast.ToastUtils
import com.xyuan.wanandroid.rxhttp.exception.ExceptionEngine
import io.reactivex.disposables.Disposable

/**
 * Created by YuanZhiQiang on 2019/03/26 13:10
 * Desc:
 */
abstract class CommonObserver<T> : BaseObserver<T>() {

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
       onStart()
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()

        val apiException = ExceptionEngine.handleException(e)
        ToastUtils.show(apiException.msg)
    }

    abstract fun onStart()

    abstract fun onSuccess(response: T)
}