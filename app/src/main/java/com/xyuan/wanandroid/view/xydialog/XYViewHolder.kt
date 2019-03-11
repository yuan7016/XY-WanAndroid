package com.xyuan.wanandroid.view.xydialog

import android.util.SparseArray
import android.view.View
import android.widget.TextView

/**
 * ViewHolder
 * @author YuanZhiQiang
 */
@Suppress("UNCHECKED_CAST")
class XYViewHolder private constructor(private val convertView: View) {
    private val views: SparseArray<View> = SparseArray()

    fun <T : View> getView(viewId: Int): T {
        var view: View? = views.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }

    companion object {

        fun create(view: View): XYViewHolder {
            return XYViewHolder(view)
        }
    }
}

fun XYViewHolder.setText(viewId: Int, textId: Int) {
    val textView = getView<TextView>(viewId)
    textView.setText(textId)
}

fun XYViewHolder.setText(viewId: Int, text: CharSequence) {
    val textView = getView<TextView>(viewId)
    textView.text = text
}

fun XYViewHolder.setTextColor(viewId: Int, colorId: Int) {
    val textView = getView<TextView>(viewId)
    textView.setTextColor(colorId)
}

fun XYViewHolder.setOnClickListener(viewId: Int, clickListener: View.OnClickListener?) {
    val view = getView<View>(viewId)
    view.setOnClickListener(clickListener)
}

fun XYViewHolder.setBackgroundResource(viewId: Int, resId: Int) {
    val view = getView<View>(viewId)
    view.setBackgroundResource(resId)
}

fun XYViewHolder.setBackgroundColor(viewId: Int, colorId: Int) {
    val view = getView<View>(viewId)
    view.setBackgroundColor(colorId)
}