package com.xyuan.wanandroid.data

/**
 * Created by YuanZhiQiang on 2019/03/25 16:48
 * Desc: 项目
 */
data class ProjectResponse(var offset: Int, var size: Int,
                           var total: Int, var pageCount: Int,
                           var curPage: Int, var over: Boolean, var datas: ArrayList<ProjectItemData>?){


    /**
     * 项目Info
     */
    data class ProjectItemData(var id: Int, var originId: Int,
                           var title: String, var chapterId: Int,
                            var apkLink: String?,
                           var chapterName: String?, var envelopePic: Any,
                           var link: String, var author: String,
                           var origin: Any, var publishTime: Long,
                           var zan: Int, var desc: Any,
                           var visible: Int, var niceDate: String,
                           var courseId: Int, var collect: Boolean,
                           var superChapterId : Int,var superChapterName : String?){

//        {
//            "apkLink": "",
//            "author": "ForgetSky",
//            "chapterId": 294,
//            "chapterName": "完整项目",
//            "collect": false,
//            "courseId": 13,
//            "desc": "项目基于 Material Design + MVP +dagger2 + RxJava + Retrofit + Glide + greendao 等架构进行设计实现，极力打造一款 优秀的玩Android  https://www.wanandroid.com  客户端，是一个不错的Android应用开发学习参考项目",
//            "envelopePic": "https://www.wanandroid.com/blogimgs/796709d5-6238-4fc7-bcbd-6346ea43cf81.png",
//            "fresh": false,
//            "id": 8120,
//            "link": "http://www.wanandroid.com/blog/show/2538",
//            "niceDate": "2019-03-23",
//            "origin": "",
//            "projectLink": "https://github.com/ForgetSky/ForgetSkyWanAndroid",
//            "publishTime": 1553342918000,
//            "superChapterId": 294,
//            "superChapterName": "开源项目主Tab",
//            "tags": [
//            {
//                "name": "项目",
//                "url": "/project/list/1?cid=294"
//            }
//            ],
//            "title": "一款精致的玩Android客户端",
//            "type": 0,
//            "userId": -1,
//            "visible": 1,
//            "zan": 0
//        }

    }


    fun hasNextPage() : Boolean{
        return curPage < pageCount && datas!!.isNotEmpty()
    }

}