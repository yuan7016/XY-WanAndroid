package com.xyuan.wanandroid.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.constant.PathManager
import kotlinx.android.synthetic.main.activity_about_me.*

/**
 * Created by YuanZhiQiang on  2019/4/10 0010 <br/>
 *  desc: 关于我
 */
@Route(path= PathManager.ABOUTME_ACTIVITY_PATH)
class AboutMeActivity  : BaseActivity(){


    override fun getContentLayoutId(): Int {
        return R.layout.activity_about_me
    }

    override fun initView() {
        initToolBar(toolbar_me,"关于我")
    }

    override fun initData() {

        tv_git.setOnClickListener {
            ARouter
                    .getInstance()
                    .build(PathManager.WEBVIEW_ACTIVITY_PATH)
                    .withString("loadUrl","https://github.com/yuan7016")
                    .withString("title","github")
                    .navigation()
        }

        tv_csdn.setOnClickListener {
            ARouter
                    .getInstance()
                    .build(PathManager.WEBVIEW_ACTIVITY_PATH)
                    .withString("loadUrl","https://blog.csdn.net/yuan7016")
                    .withString("title","csdn博客")
                    .navigation()
        }

        tv_wan_android.setOnClickListener {
            ARouter
                    .getInstance()
                    .build(PathManager.WEBVIEW_ACTIVITY_PATH)
                    .withString("loadUrl","https://www.wanandroid.com")
                    .withString("title","WanAndroid")
                    .navigation()
        }
    }
}