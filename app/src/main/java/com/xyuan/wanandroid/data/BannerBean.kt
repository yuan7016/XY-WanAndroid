package com.xyuan.wanandroid.data


/**
 * Created by YuanZhiQiang on 2019/03/27 17:17
 * Desc:Banner
 */
data class BannerBean(var id: Int, var title: String, var desc: String?, var imagePath: String,
                      var isVisible: Int,var order: Int,var type: Int,var url: String){

//    {
//        "desc": "一起来做个App吧",
//        "id": 10,
//        "imagePath": "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
//        "isVisible": 1,
//        "order": 1,
//        "title": "一起来做个App吧",
//        "type": 0,
//        "url": "http://www.wanandroid.com/blog/show/2"
//    }
}