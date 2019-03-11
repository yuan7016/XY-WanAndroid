package com.xyuan.wanandroid.view.xydialog

import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager

/**
 * XYDialog
 * @author YuanZhiQiang<p/>
 *
 * <p>
 *  XYDialog.init(supportFragmentManager)
 *  .setLayoutRes(R.layout.ldialog_share)
 *  .setBackgroundDrawableRes(R.drawable.shape_share_dialog_bg)
 *  .setGravity(Gravity.BOTTOM)
 *  .setWidthScale(0.95f)
 *  .setVerticalMargin(0.015f)
 *  .setAnimStyle(R.style.dialogBottomAnimation)
 *  .setViewHandlerListener(object : ViewHandlerListener() {
 *      override fun convertView(holder: XYViewHolder, dialog: BaseXYDialog<*>) {
 *          holder.setOnClickListener(R.id.cancelBtn, View.OnClickListener {
 *              dialog.dismiss()
 *          })
 *    }
 *  })
 *  .show()
 * </p>
 *
 */
class XYDialog : BaseXYDialog<XYDialog>() {

    override fun layoutRes(): Int = 0

    override fun layoutView(): View? = null

    override fun viewHandler(): ViewHandlerListener? {
        return null
    }

    fun setLayoutRes(@LayoutRes layoutRes: Int): XYDialog {
        baseParams.layoutRes = layoutRes
        return this
    }

    fun setLayoutView(view: View): XYDialog {
        baseParams.view = view
        return this
    }

    fun setViewHandlerListener(viewHandlerListener: ViewHandlerListener): XYDialog {
        this@XYDialog.viewHandlerListener = viewHandlerListener
        return this
    }

    companion object {
        fun init(fragmentManager: FragmentManager): XYDialog {
            return XYDialog().apply { setFragmentManager(fragmentManager) }
        }
    }
}