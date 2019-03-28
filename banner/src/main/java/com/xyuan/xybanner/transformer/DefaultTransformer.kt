package com.xyuan.xybanner.transformer

import android.view.View

class DefaultTransformer : ABaseTransformer() {

    override fun onTransform(view: View, position: Float) {}

    override var isPagingEnabled: Boolean = true

}
