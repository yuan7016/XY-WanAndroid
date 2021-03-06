package com.xyuan.wanandroid.ui

import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.toast.ToastUtils
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.listener.XYTextWatcher
import com.xyuan.wanandroid.constant.PathManager
import com.xyuan.wanandroid.data.LoginResponse
import com.xyuan.wanandroid.listener.RequestStateSubscriber
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.viewmodel.LoginRegisterViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.common_tool_bar_layout.*

/**
 * Created by YuanZhiQiang on 2019/03/18 11:00
 * Desc:注册
 */
@Route(path= PathManager.REGISTER_ACTIVITY_PATH)
class RegisterActivity : BaseActivity(){

    private val mViewModel : LoginRegisterViewModel by lazy {
        ViewModelProviders.of(this).get(LoginRegisterViewModel::class.java)
    }

    override fun getContentLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        initToolBar(toolbarCommon,"注册")

        initListener()
    }

    private fun initListener() {
        actvUserName.addTextChangedListener(object : XYTextWatcher(){
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                if (TextUtils.isEmpty(s.toString())){
                    textInputLayoutUserName.isErrorEnabled = true
                    textInputLayoutUserName.error = getString(R.string.error_invalid_account)
                }else{
                    textInputLayoutUserName.isErrorEnabled = false
                }
            }
        })

        etRegisterPassword.addTextChangedListener(object : XYTextWatcher() {

            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                if (TextUtils.isEmpty(s.toString())){
                    textInputLayoutRegisterPassword.isErrorEnabled = true
                    textInputLayoutRegisterPassword.error = getString(R.string.error_invalid_password)
                }else{
                    textInputLayoutRegisterPassword.isErrorEnabled = false
                }
            }
        })

        etConfirmPassword.addTextChangedListener(object : XYTextWatcher() {

            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                if (TextUtils.isEmpty(s.toString())){
                    textInputLayoutConfirmPassword.isErrorEnabled = true
                    textInputLayoutConfirmPassword.error = getString(R.string.error_invalid_password)
                }else{
                    textInputLayoutConfirmPassword.isErrorEnabled = false
                }
            }
        })

        btnRegister.setOnClickListener { attemptRegister() }
    }

    private fun attemptRegister() {
        // Reset errors.
        textInputLayoutUserName.error = null
        textInputLayoutRegisterPassword.error = null
        textInputLayoutConfirmPassword.error = null

        val userName = actvUserName.text.toString()
        val password = etRegisterPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()


        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)){
            //register
            toRegister(userName,password,confirmPassword)
        }else{

            if (TextUtils.isEmpty(userName)) {
                textInputLayoutUserName.isErrorEnabled = true
                textInputLayoutUserName.error = getString(com.xyuan.wanandroid.R.string.error_invalid_account)
            }

            if (TextUtils.isEmpty(password)) {
                textInputLayoutRegisterPassword.isErrorEnabled = true
                textInputLayoutRegisterPassword.error = getString(com.xyuan.wanandroid.R.string.error_invalid_password)
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                textInputLayoutConfirmPassword.isErrorEnabled = true
                textInputLayoutConfirmPassword.error = getString(com.xyuan.wanandroid.R.string.error_invalid_password)
            }

        }

    }


    override fun initData() {

    }


    private fun toRegister(userName: String, password: String, confirmPassword: String) {

        val liveData = mViewModel.getRegisterInfo(userName, password,confirmPassword,object : RequestStateSubscriber {
            override fun onStart() {
                progressBarRegister.visibility = View.VISIBLE
            }

            override fun onComplete() {
                progressBarRegister.visibility = View.GONE
            }

        })


        liveData.observe(this, Observer<LoginResponse> { t ->

            ToastUtils.show("注册成功")
            AppLog.e("==toLogin==${t.toString()}")

            finish()
        })
    }

}