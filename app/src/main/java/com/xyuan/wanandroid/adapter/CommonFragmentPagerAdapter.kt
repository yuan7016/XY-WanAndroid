package com.xyuan.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xyuan.wanandroid.util.HtmlUtil

/**
 * Created by YuanZhiQiang on 2019/4/1
 * desc: FragmentPagerAdapter  公众号 、项目
 */
class CommonFragmentPagerAdapter(fm: FragmentManager, val titles: ArrayList<String>, val fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = HtmlUtil.htmlDecode(titles[position])
}