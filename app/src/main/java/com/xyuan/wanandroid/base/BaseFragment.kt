package com.xyuan.wanandroid.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager

/**
 * Created by YuanZhiQiang on 2019/03/08 15:20.
 */
open class BaseFragment : Fragment(){

    private val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
    protected var mStatusLayoutManager : StatusLayoutManager? = null

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


    open fun initStatusLayoutManager(view : View){
        mStatusLayoutManager = StatusLayoutManager.Builder(view)
            .setOnStatusChildClickListener(object : OnStatusChildClickListener {

                override fun onEmptyChildClick(view: View?) {
                    mStatusLayoutManager?.showLoadingLayout()

                    getData()
                }

                override fun onErrorChildClick(view: View?) {
                    mStatusLayoutManager?.showLoadingLayout()

                    getData()
                }

                override fun onCustomerChildClick(view: View?) {

                }

            }).build()
    }


    open fun getData(){}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}