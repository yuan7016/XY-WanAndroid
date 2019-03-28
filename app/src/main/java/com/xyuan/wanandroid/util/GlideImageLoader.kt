package com.xyuan.wanandroid.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xyuan.xybanner.loader.ImageLoader

/**
 * Created by YuanZhiQiang on 2019/03/28 15:51
 * Desc:
 */
class GlideImageLoader : ImageLoader(){

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).into(imageView)
    }
}