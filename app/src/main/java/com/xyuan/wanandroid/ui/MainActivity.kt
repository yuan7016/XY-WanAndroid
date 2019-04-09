package com.xyuan.wanandroid.ui

import android.content.Intent
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.rxbus.RxBus
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.adapter.MainFragmentPagerAdapter
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.constant.AppConstant
import com.xyuan.wanandroid.ui.fragments.HomeFragment
import com.xyuan.wanandroid.ui.fragments.ProjectFragment
import com.xyuan.wanandroid.ui.fragments.SystemFragment
import com.xyuan.wanandroid.ui.fragments.WechatFragment
import com.xyuan.wanandroid.util.BottomNavigationViewUtil
import com.xyuan.wanandroid.constant.PathManager
import com.xyuan.wanandroid.data.LoginResponse
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * MainActivity
 */
@Route(path= PathManager.MAIN_ACTIVITY_PATH)
class MainActivity : BaseActivity() {

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


    override fun getContentLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        initActionBar()

        BottomNavigationViewUtil.disableShiftMode(navigationView)
        viewPagerMain.canScroll = false
        viewPagerMain.offscreenPageLimit = 4

    }

    override fun initData() {
        val fragmentList: ArrayList<Fragment> = arrayListOf(
            HomeFragment(),
            SystemFragment(),
            WechatFragment(),
            ProjectFragment()
        )
        viewPagerMain.adapter = MainFragmentPagerAdapter(supportFragmentManager, fragmentList)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        initListener()

        val userName = SharedPreferencesUtil.getPreferString(AppConstant.USER_NAME_KEY,"请登录")

        tvUserName.text = userName
    }


    private fun initActionBar(){
        setSupportActionBar(toolbarMain)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout_main, toolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout_main.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initListener() {
        // 注册 String 类型事件
        RxBus.getDefault().subscribe(this, object : RxBus.Callback<String>() {
            override fun onEvent(s: String) {
                AppLog.w("MainActivity==eventTag", s)
                if (s.contains("login")){
                    //登录操作后  刷新数据
                    val userName = SharedPreferencesUtil.getPreferString(AppConstant.USER_NAME_KEY)

                    if (TextUtils.isEmpty(userName)){
                        ivUserImg.isEnabled = true
                        tvUserName.text = "请登录"
                    }else{
                        ivUserImg.isEnabled = false
                        tvUserName.text = userName
                    }
                }
            }
        })


        //login
        ivUserImg.setOnClickListener {
            ARouter.getInstance().build(PathManager.LOGIN_ACTIVITY_PATH).navigation()
        }

        //设置
        ll_setting.setOnClickListener {
            ARouter.getInstance().build(PathManager.SETTING_ACTIVITY_PATH).navigation()
        }

        //我的收藏
        ll_my_collection.setOnClickListener {
            val userName = SharedPreferencesUtil.getPreferString(AppConstant.USER_NAME_KEY)

            if (TextUtils.isEmpty(userName)){
                ARouter.getInstance().build(PathManager.LOGIN_ACTIVITY_PATH).navigation()
            }else{
                Toast.makeText(this@MainActivity, "收藏", Toast.LENGTH_SHORT).show()
            }

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
