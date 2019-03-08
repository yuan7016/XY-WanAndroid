package com.xyuan.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by YuanZhiQiang on 2019/03/08 14:34.
 */
class CustomerViewPager: ViewPager {
    var canScroll  = false

    constructor(context : Context) : super(context){}

    constructor(context : Context,attrs: AttributeSet?) : super(context, attrs){}


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (canScroll) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return canScroll&&super.onTouchEvent(ev)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }

}