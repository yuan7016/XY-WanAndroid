package com.xyuan.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by YuanZhiQiang on 2019/4/1
 * desc: 公众号Adapter
 */
class WxNameAdapter(fm: FragmentManager, val titles: ArrayList<String>, val fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}