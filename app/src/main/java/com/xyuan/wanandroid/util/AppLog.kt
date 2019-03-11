package com.xyuan.wanandroid.util

import android.util.Log

/**
 * Created by YuanZhiQiang on 2019/03/11 15:23.
 */
class AppLog {

    companion object {
        private var DEBUG = true
        private val TAG = "WanAndroid"

        fun d(msg: String) {
            if (DEBUG) {
                Log.d(TAG, msg)
            }
        }

        fun d(Tag: String, msg: String) {
            if (DEBUG) {
                Log.d(Tag, msg)
            }
        }

        fun i(msg: String) {
            if (DEBUG) {
                Log.i(TAG, msg)
            }
        }

        fun i(Tag: String, msg: String) {
            if (DEBUG) {
                Log.i(Tag, msg)
            }
        }

        fun w(msg: String) {
            if (DEBUG) {
                Log.w(TAG, msg)
            }
        }

        fun w(Tag: String, msg: String) {
            if (DEBUG) {
                Log.w(Tag, msg)
            }
        }

        fun e(msg: String) {
            if (DEBUG) {
                Log.e(TAG, msg)
            }
        }

        fun e(Tag: String, msg: String) {
            if (DEBUG) {
                Log.e(Tag, msg)
            }
        }
    }
}