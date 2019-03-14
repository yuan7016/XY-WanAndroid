package com.xyuan.wanandroid.util

import com.xyuan.wanandroid.MainApplication

/**
 * Created by YuanZhiQiang on 2019/03/14 11:16.
 * dp px 转换
 */
class DensityUtil {

    companion object {
        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dp2px(dpValue: Float): Int {
            val scale = MainApplication.getContext().resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        fun px2dp(pxValue: Float): Int {
            val scale = MainApplication.getContext().resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }
    }
}