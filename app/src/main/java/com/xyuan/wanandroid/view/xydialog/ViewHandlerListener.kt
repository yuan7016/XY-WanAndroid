package com.xyuan.wanandroid.view.xydialog

import android.os.Parcel
import android.os.Parcelable

abstract class ViewHandlerListener protected constructor(source: Parcel) : Parcelable {

    abstract fun convertView(holder: XYViewHolder, dialog: BaseXYDialog<*>)

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ViewHandlerListener> = object : Parcelable.Creator<ViewHandlerListener> {
            override fun createFromParcel(source: Parcel): ViewHandlerListener {
                return object : ViewHandlerListener(source) {
                    override fun convertView(holder: XYViewHolder, dialog: BaseXYDialog<*>) {
                        dialog.dismiss()
                    }
                }
            }

            override fun newArray(size: Int): Array<ViewHandlerListener?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }
}