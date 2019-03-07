package com.xyuan.wanandroid

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.xyuan.wanandroid.util.BottomNavigationViewUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_system-> {
                message.setText(R.string.title_system)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_wechat -> {
                message.setText(R.string.title_wechat)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_project -> {
                message.setText(R.string.title_project)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        BottomNavigationViewUtil.disableShiftMode(navigationView)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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
