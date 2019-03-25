package com.xyuan.wanandroid

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.toast.ToastUtils
import com.xyuan.wanandroid.util.SharedPreferencesUtil
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig

/**
 * Created by YuanZhiQiang on 2019/03/07 15:11.
 */
class MainApplication : Application() {

    var IS_DEBUG = true

    companion object {
        private lateinit var mContext : Context

        fun getContext() : Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        mContext = applicationContext

        ToastUtils.init(this)
        SharedPreferencesUtil.init(this)
        initAutoSize()
        initARouter()
    }

    private fun initARouter() {

        if (IS_DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化

    }

    private fun initAutoSize() {
        /**
         * 以下是 AndroidAutoSize 可以自定义的参数, [AutoSizeConfig] 的每个方法的注释都写的很详细
         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
         */
        AutoSizeConfig.getInstance()
            //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
            //如果没有这个需求建议不开启
            .setCustomFragment(true)
            .isExcludeFontScale = true
        //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
        //AutoSize 会将屏幕总高度减去状态栏高度来做适配, 如果设备上有导航栏还会减去导航栏的高度
        //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏以及导航栏高度
        // .setUseDeviceSize(true)
        //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
        // .setBaseOnWidth(false)
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
        //在 Demo 中跳转的三方库中的 DefaultErrorActivity 就是在另外一个进程中, 所以要想适配这个 Activity 就需要调用 initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(this)
    }
}