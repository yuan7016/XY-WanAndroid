package com.xyuan.wanandroid.ui

import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.toast.ToastUtils
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.constant.AppConstant
import com.xyuan.wanandroid.constant.PathManager
import com.xyuan.wanandroid.data.LoginResponse
import com.xyuan.wanandroid.listener.RequestStateSubscriber
import com.xyuan.wanandroid.listener.XYTextWatcher
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.util.SharedPreferencesUtil
import com.xyuan.wanandroid.viewmodel.LoginRegisterViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.common_tool_bar_layout.*


/**
 * Created by YuanZhiQiang on 2019/03/11
 * 登录
 */
@Route(path= PathManager.LOGIN_ACTIVITY_PATH)
class LoginActivity : BaseActivity(){

    private val mViewModel : LoginRegisterViewModel by lazy {
        ViewModelProviders.of(this).get(LoginRegisterViewModel::class.java)
    }

    override fun getContentLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {

        initToolBar(toolbarCommon,"登录")

        initListener()
    }

    private fun initListener() {

        actvAccount.addTextChangedListener(object : XYTextWatcher(){
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                if (TextUtils.isEmpty(s.toString())){
                    textAccountInputLayout.isErrorEnabled = true
                    textAccountInputLayout.error = getString(R.string.error_invalid_account)
                }else{
                    textAccountInputLayout.isErrorEnabled = false
                }
            }
        })

        etPassword.addTextChangedListener(object : XYTextWatcher() {

            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                if (TextUtils.isEmpty(s.toString())){
                    textPasswordInputLayout.isErrorEnabled = true
                    textPasswordInputLayout.error = getString(R.string.error_invalid_password)
                }else{
                    textPasswordInputLayout.isErrorEnabled = false
                }
            }
        })

        btnLogin.setOnClickListener { attemptLogin() }

        tvToRegister.setOnClickListener {
            ARouter.getInstance().build(PathManager.REGISTER_ACTIVITY_PATH).navigation()
        }
    }

    override fun initData() {

    }

    /**
     * Attempts to Login
     */
    private fun attemptLogin() {
        // Reset errors.
        textAccountInputLayout.error = null
        textPasswordInputLayout.error = null

        // get account and password
        val accountStr = actvAccount.text.toString()
        val passwordStr = etPassword.text.toString()


        if(!TextUtils.isEmpty(accountStr) && !TextUtils.isEmpty(passwordStr)){
            textAccountInputLayout.isErrorEnabled = false
            textPasswordInputLayout.isErrorEnabled = false
            // login.
            toLogin(accountStr,passwordStr)
        }

        if (TextUtils.isEmpty(accountStr)) {
            textAccountInputLayout.isErrorEnabled = true
            textAccountInputLayout.error = getString(com.xyuan.wanandroid.R.string.error_invalid_account)
        }

        if (TextUtils.isEmpty(passwordStr)) {
            textPasswordInputLayout.isErrorEnabled = true
            textPasswordInputLayout.error = getString(com.xyuan.wanandroid.R.string.error_invalid_password)
        }

    }

    /**
     * 登录
     */
    private fun toLogin(accountStr: String, passwordStr: String) {

        val liveData = mViewModel.getLoginInfo(accountStr, passwordStr,object : RequestStateSubscriber{
            override fun onStart() {
                progressBarLogin.visibility = View.VISIBLE
            }

            override fun onComplete() {
                progressBarLogin.visibility = View.GONE
            }

        })


        liveData.observe(this,object : Observer<LoginResponse>{

            override fun onChanged(response: LoginResponse?) {
                if (response != null){
                    ToastUtils.show("登录成功")
                    SharedPreferencesUtil.setPreferInt(AppConstant.USER_ID_KEY,response.id)
                    SharedPreferencesUtil.setPreferString(AppConstant.USER_NAME_KEY,response.username)


                    finish()
                }

            }

        })
    }
}
