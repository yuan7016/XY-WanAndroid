package com.xyuan.wanandroid.fragments

import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseLazyLoadFragment
import kotlinx.android.synthetic.main.fragement_home.*

/**
 * Created by YuanZhiQiang on 2019/03/08 15:31.
 * 首页
 */
class HomeFragment : BaseLazyLoadFragment(){

    override fun getLayoutRes(): Int {
        return R.layout.fragement_home
    }

    override fun initView(rootView: View?) {

        //val scrollView = rootView!!.findViewById<NestedScrollView>(R.id.nestedScrollView)


    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        fab.hide()

        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY >= 4500){
                fab.show()
            }else{
                fab.hide()
            }
        }

        fab.setOnClickListener { view ->

        }
    }

}