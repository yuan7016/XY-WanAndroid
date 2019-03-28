package com.xyuan.wanandroid.ui

import android.os.Handler
import com.alibaba.android.arouter.launcher.ARouter
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.constant.PathManager
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * Created by YuanZhiQiang on 2019/03/14 11:10.
 * 启动页
 */
class SplashActivity : BaseActivity(){

    var hasLoaded = false

    override fun getContentLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()

        if (!hasLoaded){
            launcherAnimationView.start()
            launcherAnimationView.setLogoAnimationListener {
                Handler().postDelayed({
                    //跳转到主页
                    ARouter.getInstance().build(PathManager.MAIN_ACTIVITY_PATH).navigation()

                    finish()
                }, 1000)
            }
            hasLoaded = true
        }

    }
}