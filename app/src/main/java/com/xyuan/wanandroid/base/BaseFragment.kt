package com.xyuan.wanandroid.base

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by YuanZhiQiang on 2019/03/08 15:20.
 */
open class BaseFragment : Fragment(){

    private val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {//解决fragment重叠的问题
            val isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
            val ft = fragmentManager!!.beginTransaction()
            if (isSupportHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }
            ft.commit()
        }
    }


    fun showLoading() {

    }

    fun dismissLoading() {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}