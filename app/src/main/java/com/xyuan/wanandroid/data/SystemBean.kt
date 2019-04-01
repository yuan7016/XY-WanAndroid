package com.xyuan.wanandroid.data

/**
 * Created by YuanZhiQiang on  2019/3/29 0029 <br/>
 *  desc: 体系
 */
data class SystemBean(var courseId : Int,var id : Int,var name:String,var visible : Int,
                      var order : Int,var parentChapterId:Int,var children : ArrayList<ChildrenTree>) {

//    {
//        "children": [
//        {
//            "children": [],
//            "courseId": 13,
//            "id": 60,
//            "name": "Android Studio相关",
//            "order": 1000,
//            "parentChapterId": 150,
//            "visible": 1
//        }
//        ],
//        "courseId": 13,
//        "id": 150,
//        "name": "开发环境",
//        "order": 1,
//        "parentChapterId": 0,
//        "visible": 1
//    }


    data class ChildrenTree(var courseId : Int,var id : Int,var name:String,var visible : Int,
                            var order : Int,var parentChapterId:Int,var children : ArrayList<ChildrenTree>?){


    }
}