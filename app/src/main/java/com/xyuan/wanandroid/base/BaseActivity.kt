package com.xyuan.wanandroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by YuanZhiQiang on 2019/03/08 14:30.
 */
abstract class BaseActivity : AppCompatActivity(){

    /**
     * 获取显示view的xml文件ID
     */
    protected abstract fun getContentLayoutId(): Int

    /**
     * 初始化view
     */
    protected abstract fun initView()

    /**
     * 初始化Data
     */
    protected abstract fun initData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getContentLayoutId())

        initView()

        initData()
    }
}