package com.xyuan.wanandroid.rxhttp.transformer


import com.xyuan.wanandroid.data.BaseResponse
import com.xyuan.wanandroid.rxhttp.exception.ApiException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by YuanZQ
 * IO和主线程切换
 */

object RxTransformer {


    fun <T> switchIOAndMainThread(): ObservableTransformer<T, T> {

        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    }


    /**
     * Transformer: 将BaseResponseBean 转换为T
     * @param <T>
     * @return
    </T> */
    fun <T> rxCompose(): ObservableTransformer<BaseResponse<T>, T> {
        return ObservableTransformer { upstream ->
            upstream.flatMap(object : Function<BaseResponse<T>, ObservableSource<T>> {

                override fun apply(tBaseResponseBean: BaseResponse<T>): ObservableSource<T> {
                    // 根据后台基本格式进行转换
                    return if (tBaseResponseBean != null && tBaseResponseBean.errorCode == 0) {
                        Observable.create { emitter ->
                            try {
                                emitter.onNext(tBaseResponseBean.data)
                                emitter.onComplete()

                            } catch (e: Exception) {
                                emitter.onError(e)
                            }
                        }
                    } else {
                        Observable.create { emitter ->
                            emitter.onError(
                                ApiException(tBaseResponseBean.errorCode, tBaseResponseBean.errorMsg!!)
                            )
                        }
                    }
                }
            }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
