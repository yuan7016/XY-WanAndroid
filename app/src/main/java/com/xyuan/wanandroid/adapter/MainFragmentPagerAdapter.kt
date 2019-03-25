package com.xyuan.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * Created by YuanZhiQiang on 2019/2/26
 * desc:
 */
class MainFragmentPagerAdapter(fm: FragmentManager, fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    private var mFragments: ArrayList<Fragment>? = fragments

    override fun getItem(position: Int): Fragment {
        return mFragments!![position]
    }

    override fun getCount(): Int {
        return mFragments?.size ?: 0
    }
}