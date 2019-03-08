package com.xyuan.wanandroid.ui

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.home.HomeFragment
import com.xyuan.wanandroid.system.ProjectFragment
import com.xyuan.wanandroid.system.SystemFragment
import com.xyuan.wanandroid.system.WechatFragment
import com.xyuan.wanandroid.util.BottomNavigationViewUtil
import kotlinx.android.synthetic.main.activity_main.*
import nmr.kmbb.smartmedical.adapter.MainFragmentPagerAdapter
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPagerMain.setCurrentItem(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_system -> {
                viewPagerMain.setCurrentItem(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_wechat -> {
                viewPagerMain.setCurrentItem(2)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_project -> {
                viewPagerMain.setCurrentItem(3)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragmentList: ArrayList<Fragment> = arrayListOf(HomeFragment(), SystemFragment(), WechatFragment(), ProjectFragment())

        viewPagerMain.canScroll = false
        viewPagerMain.adapter = MainFragmentPagerAdapter(supportFragmentManager, fragmentList)
        viewPagerMain.offscreenPageLimit = 4

        BottomNavigationViewUtil.disableShiftMode(navigationView)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        initListener()
    }

    private fun initListener() {

        ll_setting.setOnClickListener {
            Toast.makeText(this@MainActivity, "设置", Toast.LENGTH_SHORT).show()
        }

        ll_my_collection.setOnClickListener {
            Toast.makeText(this@MainActivity, "收藏", Toast.LENGTH_SHORT).show()
        }

        ll_about_us.setOnClickListener {
            Toast.makeText(this@MainActivity, "关于我", Toast.LENGTH_SHORT).show()
        }
    }


    //记录用户首次点击返回键的时间
    private var firstTime: Long = 0
    /**
     * 再按一次退出程序
     * @param keyCode
     * @param event
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            val secondTime = System.currentTimeMillis()
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this@MainActivity, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                firstTime = secondTime
                return true
            } else {
                System.exit(0)
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
