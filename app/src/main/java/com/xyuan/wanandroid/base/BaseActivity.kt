package com.xyuan.wanandroid.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.launcher.ARouter
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager

/**
 * Created by YuanZhiQiang on 2019/03/08 14:30.
 */
abstract class BaseActivity : AppCompatActivity(){

    protected lateinit var mStatusLayoutManager: StatusLayoutManager

    /**
     * 获取显示view的xml文件ID
     */
    protected abstract fun getContentLayoutId(): Int

    /**
     * 初始化view
     */
    protected abstract fun initView()

    /**
     * 初始化Data
     */
    protected abstract fun initData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getContentLayoutId())

        //
        ARouter.getInstance().inject(this)

        initView()

        initData()
    }


    protected fun initToolBar(toolBarView : Toolbar,toolBarTitle : String) {
        setSupportActionBar(toolBarView)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.title = toolBarTitle
        toolBarView.setNavigationOnClickListener {
            onBackPressed()
        }
    }



    open fun initStatusLayoutManager(view : View){
        mStatusLayoutManager = StatusLayoutManager.Builder(view)
                .setOnStatusChildClickListener(object : OnStatusChildClickListener {
                    override fun onEmptyChildClick(view: View) {
                        mStatusLayoutManager.showLoadingLayout()

                        getData()
                    }

                    override fun onErrorChildClick(view: View) {
                        mStatusLayoutManager.showLoadingLayout()

                        getData()
                    }

                    override fun onCustomerChildClick(view: View) {
                        //onCustomerChildClick
                    }

                }).build()
    }

    /**
     * 重新获取data
     */
    open fun getData(){}
}