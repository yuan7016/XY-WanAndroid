package com.xyuan.wanandroid.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.data.ProjectResponse
import com.xyuan.wanandroid.util.HtmlUtil

/**
 * Created by YuanZhiQiang on  2019/4/3 0003 <br/>
 *  desc: 项目Adapter
 */
class ProjectAdapter : BaseQuickAdapter<ProjectResponse.ProjectItemData, BaseViewHolder> {

    constructor(list : ArrayList<ProjectResponse.ProjectItemData>) : super(R.layout.item_project_layout , list)


    override fun convert(helper: BaseViewHolder, item: ProjectResponse.ProjectItemData) {
        val tvTitle = helper.getView<TextView>(R.id.tv_item_project_title)
        val tvDesc = helper.getView<TextView>(R.id.tv_item_project_desc)
        val tvTime = helper.getView<TextView>(R.id.tv_item_project_time)
        val tvAuthor = helper.getView<TextView>(R.id.tv_item_project_author)
        val imageView = helper.getView<ImageView>(R.id.iv_item_project)

        tvTitle.text = HtmlUtil.htmlDecode(item.title)
        tvDesc.text = item.desc.toString()
        tvTime.text = item.niceDate
        tvAuthor.text = item.author

        val options = RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(mContext)
                .load(item.envelopePic)
                .apply(options)
                .into(imageView)
    }
}