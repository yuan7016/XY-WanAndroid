package com.xyuan.wanandroid.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.data.SystemBean

/**
 * Created by YuanZhiQiang on  2019/4/1 <br/>
 *  desc: 体系Adapter
 */
class SystemAdapter : BaseQuickAdapter<SystemBean, BaseViewHolder>{

    constructor(list : ArrayList<SystemBean>) : super(R.layout.item_system_layout, list)

    override fun convert(helper: BaseViewHolder?, item: SystemBean) {

        val tvTitle = helper?.getView<TextView>(R.id.tv_item_tree_title)
        val tvContent = helper?.getView<TextView>(R.id.tv_item_tree_content)

        tvTitle!!.text = item.name

        item.children.let { children ->
            tvContent!!.text = children.joinToString("     ", transform = { child ->
                child.name
            })
        }
    }

}