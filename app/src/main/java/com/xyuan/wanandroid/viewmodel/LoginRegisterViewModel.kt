package com.xyuan.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hjq.toast.ToastUtils
import com.xyuan.wanandroid.data.LoginResponse
import com.xyuan.wanandroid.listener.RequestStateSubscriber
import com.xyuan.wanandroid.rxhttp.RxHttpHelper
import com.xyuan.wanandroid.rxhttp.exception.ExceptionEngine
import com.xyuan.wanandroid.rxhttp.transformer.RxTransformer
import com.xyuan.wanandroid.util.AppLog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Created by YuanZhiQiang on 2019/03/22 11:25
 * Desc:登录注册的ViewModel
 */
class LoginRegisterViewModel : ViewModel() {


    fun getLoginInfo(userName: String, password: String, subscriber: RequestStateSubscriber): MutableLiveData<LoginResponse> {

        val liveData = MutableLiveData<LoginResponse>()

        RxHttpHelper
                .getApiService()
                .login(userName, password)
                .delay(500, TimeUnit.MILLISECONDS)
                .compose(RxTransformer.rxCompose())
                .subscribe(object : Observer<LoginResponse> {
                    override fun onComplete() {
                        AppLog.w("===onComplete====")
                        subscriber.onComplete()
                    }

                    override fun onSubscribe(d: Disposable) {
                        subscriber.onStart()
                    }

                    override fun onNext(t: LoginResponse) {
                        liveData.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        subscriber.onComplete()
                        val apiException = ExceptionEngine.handleException(e)
                        AppLog.w("==apiException==${apiException.msg}")
                        ToastUtils.show(apiException.msg)
                    }

                })


        return liveData
    }


    fun getRegisterInfo(userName: String, password: String, repassword: String, subscriber: RequestStateSubscriber): MutableLiveData<LoginResponse> {

        val liveData = MutableLiveData<LoginResponse>()

        RxHttpHelper
                .getApiService()
                .register(userName, password, repassword)
                .delay(500, TimeUnit.MILLISECONDS)
                .compose(RxTransformer.rxCompose())
                .subscribe(object : Observer<LoginResponse> {
                    override fun onComplete() {
                        subscriber.onComplete()
                    }

                    override fun onSubscribe(d: Disposable) {
                        subscriber.onStart()
                    }

                    override fun onNext(t: LoginResponse) {
                        liveData.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        subscriber.onComplete()
                        val apiException = ExceptionEngine.handleException(e)
                        ToastUtils.show(apiException.msg)
                    }
                })


        return liveData
    }
}