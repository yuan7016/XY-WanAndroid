package com.xyuan.xybanner


import androidx.viewpager.widget.ViewPager.PageTransformer
import com.xyuan.xybanner.transformer.*

/**
 * 轮播Transformer样式
 */
object Transformer {
    //默认
    var Default: Class<out PageTransformer> = DefaultTransformer::class.java
    var Accordion: Class<out PageTransformer> = AccordionTransformer::class.java
    var ForegroundToBackground: Class<out PageTransformer> = ForegroundToBackgroundTransformer::class.java
    var CubeIn: Class<out PageTransformer> = CubeInTransformer::class.java
    var Tablet: Class<out PageTransformer> = TabletTransformer::class.java
}
