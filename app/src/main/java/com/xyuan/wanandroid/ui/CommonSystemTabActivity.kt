package com.xyuan.wanandroid.ui

import androidx.fragment.app.Fragment
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.adapter.CommonFragmentPagerAdapter
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.data.SystemBean
import com.xyuan.wanandroid.ui.fragments.CommonSystemFragment
import kotlinx.android.synthetic.main.activity_system_tab.*
import kotlinx.android.synthetic.main.common_tool_bar_layout.*

/**
 * Created by YuanZhiQiang on  2019/4/11 0011 <br/>
 *  desc: 体系tab列表
 */

class CommonSystemTabActivity : BaseActivity(){

    lateinit var title : String
    lateinit var tabList : ArrayList<SystemBean.ChildrenTree>


    override fun getContentLayoutId(): Int {
        return R.layout.activity_system_tab
    }

    override fun initView() {
        title = intent.getStringExtra("title")
        tabList = intent.getSerializableExtra("tabList") as ArrayList<SystemBean.ChildrenTree>

        //设置toolbar
        initToolBar(toolbarCommon,title)

        // 关联 viewPage 和 tabLayout
        tabLayoutSystemTab.setupWithViewPager(viewPagerSystemTab)
    }

    override fun initData() {

        getSystemTabName()
    }

    private fun getSystemTabName() {


        if (tabList.isNotEmpty()){

            val titles = arrayListOf<String>()
            val fragments = arrayListOf<Fragment>()

            viewPagerSystemTab.offscreenPageLimit = tabList.size

            for (data in tabList) {
                titles.add(data.name)
                fragments.add(CommonSystemFragment.getNewInstance(data.id , data.name))
            }

            viewPagerSystemTab.adapter = CommonFragmentPagerAdapter(supportFragmentManager, titles, fragments)

        }

    }

}