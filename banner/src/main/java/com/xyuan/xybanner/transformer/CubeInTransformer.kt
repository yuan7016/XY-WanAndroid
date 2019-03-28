package com.xyuan.xybanner.transformer

import android.view.View

class CubeInTransformer : ABaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        // Rotate the fragment on the left or right edge
        view.pivotX = (if (position > 0) 0 else view.width).toFloat()
        view.pivotY = 0f
        view.rotationY = -90f * position
    }

    override var isPagingEnabled: Boolean = true
}
