package com.xyuan.wanandroid.data

/**
 * Created by YuanZhiQiang on 2019/03/25 16:48
 * Desc:文章
 */
data class ArticleResponse(var offset: Int, var size: Int,
                           var total: Int, var pageCount: Int,
                           var curPage: Int, var over: Boolean, var datas: ArrayList<ArticleData>?){


    /**
     * 文章Info
     */
    data class ArticleData(var id: Int, var originId: Int,
                           var title: String, var chapterId: Int,
                           var chapterName: String?, var envelopePic: Any,
                           var link: String, var author: String,
                           var origin: Any, var publishTime: Long,
                           var zan: Int, var desc: Any,
                           var visible: Int, var niceDate: String,
                           var courseId: Int, var collect: Boolean,
                           var superChapterId : Int,var superChapterName : String?)


    fun hasNextPage() : Boolean{
        return curPage < pageCount && datas!!.isNotEmpty()
    }

    /**
     * 文章Info
     */
//    data class ArticleData(var id: Int, var title: String, var author: String, var niceDate: String)
}