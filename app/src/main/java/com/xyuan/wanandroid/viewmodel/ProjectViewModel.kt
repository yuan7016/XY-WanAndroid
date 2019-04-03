package com.xyuan.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xyuan.wanandroid.data.*
import com.xyuan.wanandroid.rxhttp.ApiService
import com.xyuan.wanandroid.rxhttp.RxHttpHelper
import com.xyuan.wanandroid.rxhttp.rxcommon.CommonObserver
import com.xyuan.wanandroid.rxhttp.transformer.RxTransformer
import java.util.concurrent.TimeUnit

/**
 * Created by YuanZhiQiang on  2019/4/3 0003 <br/>
 *  desc: 项目ViewModel
 */
class ProjectViewModel : ViewModel(){

    private val apiService : ApiService = RxHttpHelper.getApiService()


    fun getProjectName()  : MutableLiveData<Resource<ArrayList<CommonTabNameBean>>> {
        val liveData = MutableLiveData<Resource<ArrayList<CommonTabNameBean>>>()

        apiService
                .getProjectName()
                .compose(RxTransformer.rxCompose())
                .subscribe(object : CommonObserver<ArrayList<CommonTabNameBean>>(){
                    override fun onStart() {
                        //onStart
                    }

                    override fun onSuccess(response: ArrayList<CommonTabNameBean>) {
                        liveData.postValue(Resource(Resource.SUCCESS,response,null))
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)

                        liveData.postValue(Resource(Resource.ERROR,null,e))
                    }

                })

        return liveData
    }

    fun getProjectListData(cid : Int , page : Int)  : MutableLiveData<Resource<ProjectResponse>>{
        val liveData = MutableLiveData<Resource<ProjectResponse>>()

        apiService
                .getProjectList(page , cid)
                .delay(500, TimeUnit.MILLISECONDS)
                .compose(RxTransformer.rxCompose())
                .subscribe(object : CommonObserver<ProjectResponse>(){
                    override fun onStart() {
                        liveData.postValue(Resource(Resource.START,null,null))
                    }

                    override fun onSuccess(response: ProjectResponse) {
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