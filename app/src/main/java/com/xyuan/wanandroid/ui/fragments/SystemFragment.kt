package com.xyuan.wanandroid.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseFragment

/**
 * Created by YuanZhiQiang on 2019/03/08 15:31.
 * 体系
 */
class SystemFragment : BaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragement_system,container,false)
    }
}