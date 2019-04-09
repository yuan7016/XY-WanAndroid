package com.xyuan.wanandroid.ui

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.rxbus.RxBus
import com.hjq.toast.ToastUtils
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.constant.AppConstant
import com.xyuan.wanandroid.constant.PathManager
import com.xyuan.wanandroid.data.EmptyResponse
import com.xyuan.wanandroid.listener.BaseLiveDataObserver
import com.xyuan.wanandroid.util.SharedPreferencesUtil
import com.xyuan.wanandroid.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.common_tool_bar_layout.*

/**
 * Created by YuanZhiQiang on  2019/4/9 0009 <br/>
 *  desc: 设置
 */
@Route(path= PathManager.SETTING_ACTIVITY_PATH)
class SettingActivity : BaseActivity(){

    private val mViewModel : SettingViewModel by lazy {
        ViewModelProviders.of(this).get(SettingViewModel::class.java)
    }

    override fun getContentLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {

        initToolBar(toolbarCommon,"设置")

        tv_clear_cache.setOnClickListener {
            ToastUtils.show("清除缓存")
        }

        btn_loginOut.setOnClickListener {
            loginOut()
        }
    }

    private fun loginOut() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("确定退出登录吗?")
        builder.setMessage("退出登录后，某些功能或许不可用")
        builder.setNegativeButton("取消",null)
        builder.setPositiveButton("确定") { dialog, which ->
            mViewModel.outLogin().observe(this,object : BaseLiveDataObserver<EmptyResponse>(){
                override fun onSuccess(response: EmptyResponse) {
                    if (response.success){
                        SharedPreferencesUtil.clearAllSPData()
                        RxBus.getDefault().post(AppConstant.EVENT_LOGIN_OUT)

                        ToastUtils.show("退出成功")

                        finish()
                    }
                }

            })
            dialog.dismiss()
        }

        builder.create().show()

    }

    override fun initData() {

    }
}