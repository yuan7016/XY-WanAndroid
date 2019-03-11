package com.xyuan.wanandroid.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.home.HomeFragment
import com.xyuan.wanandroid.system.ProjectFragment
import com.xyuan.wanandroid.system.SystemFragment
import com.xyuan.wanandroid.system.WechatFragment
import com.xyuan.wanandroid.util.BottomNavigationViewUtil
import kotlinx.android.synthetic.main.activity_main.*
import nmr.kmbb.smartmedical.adapter.MainFragmentPagerAdapter
import java.util.*


class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            com.xyuan.wanandroid.R.id.navigation_home -> {
                viewPagerMain.currentItem = 0
                toolbarMain.title = getString(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            com.xyuan.wanandroid.R.id.navigation_system -> {
                viewPagerMain.currentItem = 1
                toolbarMain.title = getString(R.string.title_system)
                return@OnNavigationItemSelectedListener true
            }
            com.xyuan.wanandroid.R.id.navigation_wechat -> {
                viewPagerMain.currentItem = 2
                toolbarMain.title = getString(R.string.title_wechat)
                return@OnNavigationItemSelectedListener true
            }

            com.xyuan.wanandroid.R.id.navigation_project -> {
                viewPagerMain.currentItem = 3
                toolbarMain.title = getString(R.string.title_project)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.xyuan.wanandroid.R.layout.activity_main)

        initActionBar()

        val fragmentList: ArrayList<Fragment> = arrayListOf(HomeFragment(), SystemFragment(), WechatFragment(), ProjectFragment())

        viewPagerMain.canScroll = false
        viewPagerMain.adapter = MainFragmentPagerAdapter(supportFragmentManager, fragmentList)
        viewPagerMain.offscreenPageLimit = 4

        BottomNavigationViewUtil.disableShiftMode(navigationView)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        initListener()
    }

    private fun initActionBar(){
        setSupportActionBar(toolbarMain)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout_main, toolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout_main.addDrawerListener(toggle)
        toggle.syncState()
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_action_bar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item!!.itemId) {
            R.id.ab_search -> {
                Toast.makeText(this, "你点击了“search”按键！", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
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
