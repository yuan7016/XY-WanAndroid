package com.xyuan.xybanner

/**
 * Banner配置
 */
object BannerConfig {

    //indicator style
    /**
     * 无指示器
     */
    const  val NOT_INDICATOR = 0
    /**
     * 圆形shape指示器
     */
    const val CIRCLE_INDICATOR = 1
    /**
     * 数字指示器
     */
    const val NUM_INDICATOR = 2
    /**
     * 数字和标题 指示器
     */
    const val NUM_INDICATOR_TITLE = 3
    /**
     * 圆形shape和标题 指示器
     */
    const val CIRCLE_INDICATOR_TITLE = 4
    /**
     * 圆形shape和标题 指示器
     */
    const val CIRCLE_INDICATOR_TITLE_INSIDE = 5
    /**
     * 自定义 指示器
     */
    const val CUSTOM_INDICATOR = 6

    /**
     * indicator 位置
     */
    const val LEFT = 5
    const val CENTER = 6
    const  val RIGHT = 7

    /**
     * banner_layout
     */
    const val PADDING_SIZE = 5
    const  val MARGIN_BOTTOM = 10
    /**
     * 轮播时间
     */
    const val TIME = 2000
    const val DURATION = 800
    /**
     * 自动轮播
     */
    const  val IS_AUTO_PLAY = true
    const  val IS_SCROLL = true
    /**
     * 循环轮播
     */
    val IS_LOOP = true

    /**
     * title style
     */
    const  val TITLE_BACKGROUND = -1
    const  val TITLE_HEIGHT = -1
    const  val TITLE_TEXT_COLOR = -1
    const val TITLE_TEXT_SIZE = -1

    /**
     * margin
     */
    const val PAGE_MARGIN = 0

    /**
     * arc
     */
    const val ARC_HEIGHT = 0
    const val ARC_BACKGROUND = -0x1
    const val ARC_DIRECTION = 0

}
