package com.xyuan.wanandroid.view.header

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.view.View
import android.widget.ImageView


/**
 * Private class created to work around issues with AnimationListeners being
 * called before the animation is actually complete and support shadows on older
 * platforms.
 */
@SuppressLint("ViewConstructor")
class CircleImageView(context: Context, color: Int) : ImageView(context) {

    //    private Animation.AnimationListener mListener;
    internal var mShadowRadius: Int = 0

    init {
        val thisView = this
        val density = thisView.resources.displayMetrics.density
        val shadowYOffset = (density * Y_OFFSET).toInt()
        val shadowXOffset = (density * X_OFFSET).toInt()

        mShadowRadius = (density * SHADOW_RADIUS).toInt()

        val circle: ShapeDrawable
        if (Build.VERSION.SDK_INT >= 21) {
            circle = ShapeDrawable(OvalShape())
            thisView.elevation = SHADOW_ELEVATION * density
        } else {
            val oval = OvalShadow(mShadowRadius)
            circle = ShapeDrawable(oval)
            thisView.setLayerType(View.LAYER_TYPE_SOFTWARE, circle.paint)
            circle.paint.setShadowLayer(mShadowRadius.toFloat(), shadowXOffset.toFloat(), shadowYOffset.toFloat(),
                    KEY_SHADOW_COLOR)
            val padding = mShadowRadius
            // set padding so the inner image sits correctly within the shadow.
            thisView.setPadding(padding, padding, padding, padding)
        }
        circle.paint.color = color

        thisView.background = circle

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val thisView = this
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (Build.VERSION.SDK_INT < 21) {
            super.setMeasuredDimension(
                    thisView.measuredWidth + mShadowRadius * 2,
                    thisView.measuredHeight + mShadowRadius * 2)
        }
    }


     inner class OvalShadow internal constructor(shadowRadius: Int) : OvalShape() {
        private var mRadialGradient: RadialGradient? = null
        private val mShadowPaint: Paint

        init {
            mShadowPaint = Paint()
            mShadowRadius = shadowRadius
            updateRadialGradient(super.rect().width().toInt())
        }

        override fun onResize(width: Float, height: Float) {
            super.onResize(width, height)
            updateRadialGradient(width.toInt())
        }

        override fun draw(canvas: Canvas, paint: Paint) {
            val thisView = this@CircleImageView
            val viewWidth = thisView.width
            val viewHeight = thisView.height
            canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), (viewWidth / 2).toFloat(), mShadowPaint)
            canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), (viewWidth / 2 - mShadowRadius).toFloat(), paint)
        }

        private fun updateRadialGradient(diameter: Int) {
            mRadialGradient = RadialGradient((diameter / 2).toFloat(), (diameter / 2).toFloat(),
                    mShadowRadius.toFloat(), intArrayOf(FILL_SHADOW_COLOR, Color.TRANSPARENT),
                    null, Shader.TileMode.CLAMP)
            mShadowPaint.shader = mRadialGradient
        }
    }

    companion object {

        protected val KEY_SHADOW_COLOR = 0x1E000000
        protected val FILL_SHADOW_COLOR = 0x3D000000
        // PX
        protected val X_OFFSET = 0f
        protected val Y_OFFSET = 1.75f
        protected val SHADOW_RADIUS = 3.5f
        protected val SHADOW_ELEVATION = 4
    }
}
