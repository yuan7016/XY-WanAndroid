package com.xyuan.wanandroid.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseFragment

/**
 * Created by YuanZhiQiang on 2019/03/08 15:31.
 * 项目
 */
class ProjectFragment : BaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragement_project,container,false)
    }
}