package com.xyuan.wanandroid.view.xydialog

import android.content.DialogInterface
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by YuanZhiQiang on 2019/2/25.
 * @author YuanZhiQiang
 */
abstract class OnDialogDismissListener : DialogInterface.OnDismissListener, Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {}

    constructor()

    protected constructor(source: Parcel)

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OnDialogDismissListener> = object : Parcelable.Creator<OnDialogDismissListener> {
            override fun createFromParcel(source: Parcel): OnDialogDismissListener {
                return object : OnDialogDismissListener(source) {
                    override fun onDismiss(dialog: DialogInterface) {

                    }
                }
            }

            override fun newArray(size: Int): Array<OnDialogDismissListener?> {
                return arrayOfNulls(size)
            }
        }
    }
}