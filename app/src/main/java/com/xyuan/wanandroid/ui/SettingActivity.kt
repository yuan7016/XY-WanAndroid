package com.xyuan.wanandroid.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.constant.PathManager

/**
 * Created by YuanZhiQiang on  2019/4/9 0009 <br/>
 *  desc: 设置
 */
@Route(path= PathManager.SETTING_ACTIVITY_PATH)
class SettingActivity : BaseActivity(){

    override fun getContentLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {

    }

    override fun initData() {

    }
}