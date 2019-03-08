package com.xyuan.wanandroid.util

import android.annotation.SuppressLint
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

/**
 * Created by YuanZhiQiang on 2019/03/07 14:24.
 */
 class BottomNavigationViewUtil {
    companion object {
        /**
         * 解决BottomNavigationView大于3个item时的位移
         * @param view
         */
        @SuppressLint("RestrictedApi")
        fun disableShiftMode(view: BottomNavigationView) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            try {
                menuView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED // 1
                for (i in 0 until menuView.childCount) {
                    val item = menuView.getChildAt(i) as BottomNavigationItemView
                    item.setShifting(false)
                }
            } catch (e: Exception) {
                Log.e("NavigationView", "Unable to get shift mode field", e)
            }

        }
    }

}