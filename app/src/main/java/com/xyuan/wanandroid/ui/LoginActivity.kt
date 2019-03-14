package com.xyuan.wanandroid.ui

import android.text.Editable
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.toast.ToastUtils
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.listener.XYTextWatcher
import com.xyuan.wanandroid.util.PathManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.common_tool_bar_layout.*


/**
 * Created by YuanZhiQiang on 2019/03/11
 * 登录
 */
@Route(path= PathManager.LOGIN_ACTIVITY_PATH)
class LoginActivity : BaseActivity(){

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
            ToastUtils.show("注册")
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
            ToastUtils.show("Login")
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
}
