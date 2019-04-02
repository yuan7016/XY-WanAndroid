package com.xyuan.wanandroid.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.data.ArticleResponse
import com.xyuan.wanandroid.util.HtmlUtil

/**
 * Created by YuanZhiQiang on 2019/03/25 16:40
 * Desc:首页文章Adapter
 */
class HomeAdapter : BaseQuickAdapter<ArticleResponse.ArticleData, BaseViewHolder> {

    constructor(data: ArrayList<ArticleResponse.ArticleData>) : super(R.layout.item_home_blog, data)

    override fun convert(helper: BaseViewHolder, item: ArticleResponse.ArticleData) {
        val tvTitle = helper.getView<TextView>(R.id.tv_item_home_title)
        val tvTime = helper.getView<TextView>(R.id.tv_item_home_time)
        val tvAuthor = helper.getView<TextView>(R.id.tv_item_home_name)
        val ivCollect = helper.getView<ImageView>(R.id.iv_item_home_favorite)

        tvTitle?.text = HtmlUtil.htmlDecode(item.title)
        tvTime?.text = item.niceDate
        tvAuthor?.text = item.author

        if (item.collect){
            ivCollect.setImageResource(R.drawable.icon_favorite_blue)
        }else{
            ivCollect.setImageResource(R.drawable.icon_un_favorite_blue)
        }

        helper.addOnClickListener(R.id.iv_item_home_favorite)
    }
}