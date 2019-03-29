package com.xyuan.wanandroid.view.header

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.getSize
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.VisibleForTesting

import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.internal.InternalAbstract
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.scwang.smartrefresh.layout.util.SmartUtil
import com.xyuan.wanandroid.R

/**
 * Material 主题下拉头
 * Created by SCWANG on 2017/6/2.
 */
class MaterialHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : InternalAbstract(context, attrs, defStyleAttr), RefreshHeader {

    protected var mFinished: Boolean = false
    protected var mCircleDiameter: Int = 0
    protected var mCircleView: ImageView
    protected var mProgress: MaterialProgressDrawable

    /**
     * 贝塞尔背景
     */
    protected var mWaveHeight: Int = 0
    protected var mHeadHeight: Int = 0
    protected var mBezierPath: Path
    protected var mBezierPaint: Paint
    protected var mShowBezierWave = false
    protected var mState: RefreshState? = null

    init {

        mSpinnerStyle = SpinnerStyle.MatchLayout
        val thisView = this
        val thisGroup = this
        thisView.minimumHeight = DensityUtil.dp2px(100f)

        mProgress = MaterialProgressDrawable(this)
        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT)
        mProgress.alpha = 255
        mProgress.setColorSchemeColors(-0xff6634, -0xbbbc, -0x996700, -0x559934, -0x7800)
        mCircleView = CircleImageView(context, CIRCLE_BG_LIGHT)
        mCircleView.setImageDrawable(mProgress)
        mCircleView.alpha = 0f
        thisGroup.addView(mCircleView)

        val metrics = thisView.resources.displayMetrics
        mCircleDiameter = (CIRCLE_DIAMETER * metrics.density).toInt()

        mBezierPath = Path()
        mBezierPaint = Paint()
        mBezierPaint.isAntiAlias = true
        mBezierPaint.style = Paint.Style.FILL

        val ta = context.obtainStyledAttributes(attrs, R.styleable.MaterialHeader)
        mShowBezierWave = ta.getBoolean(R.styleable.MaterialHeader_mhShowBezierWave, mShowBezierWave)
        mBezierPaint.color = ta.getColor(R.styleable.MaterialHeader_mhPrimaryColor, -0xee4401)
        if (ta.hasValue(R.styleable.MaterialHeader_mhShadowRadius)) {
            val radius = ta.getDimensionPixelOffset(R.styleable.MaterialHeader_mhShadowRadius, 0)
            val color = ta.getColor(R.styleable.MaterialHeader_mhShadowColor, -0x1000000)
            mBezierPaint.setShadowLayer(radius.toFloat(), 0f, 0f, color)
            thisView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        ta.recycle()

    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec))
        val circleView = mCircleView
        circleView.measure(View.MeasureSpec.makeMeasureSpec(mCircleDiameter, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(mCircleDiameter, View.MeasureSpec.EXACTLY))
        //        setMeasuredDimension(resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec),
        //                resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val thisGroup = this
        if (thisGroup.childCount == 0) {
            return
        }
        val thisView = this
        val circleView = mCircleView
        val width = thisView.measuredWidth
        val circleWidth = circleView.measuredWidth
        val circleHeight = circleView.measuredHeight

        if (thisView.isInEditMode && mHeadHeight > 0) {
            val circleTop = mHeadHeight - circleHeight / 2
            circleView.layout(width / 2 - circleWidth / 2, circleTop,
                    width / 2 + circleWidth / 2, circleTop + circleHeight)

            mProgress.showArrow(true)
            mProgress.setStartEndTrim(0f, MAX_PROGRESS_ANGLE)
            mProgress.setArrowScale(1f)
            circleView.alpha = 1f
            circleView.visibility = View.VISIBLE
        } else {
            circleView.layout(width / 2 - circleWidth / 2, -circleHeight,
                    width / 2 + circleWidth / 2, 0)
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (mShowBezierWave) {
            //重置画笔
            mBezierPath.reset()
            mBezierPath.lineTo(0f, mHeadHeight.toFloat())
            //绘制贝塞尔曲线
            val thisView = this
            mBezierPath.quadTo((thisView.measuredWidth / 2).toFloat(), mHeadHeight + mWaveHeight * 1.9f, thisView.measuredWidth.toFloat(), mHeadHeight.toFloat())
            mBezierPath.lineTo(thisView.measuredWidth.toFloat(), 0f)
            canvas.drawPath(mBezierPath, mBezierPaint)
        }
        super.dispatchDraw(canvas)
    }

    //</editor-fold>

    //<editor-fold desc="RefreshHeader">
    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
        val thisView = this
        if (!mShowBezierWave) {
            kernel.requestDefaultTranslationContentFor(this, false)
            //            kernel.requestDefaultHeaderTranslationContent(false);
        }
        if (thisView.isInEditMode) {
            mHeadHeight = height / 2
            mWaveHeight = mHeadHeight
        }
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
        if (mShowBezierWave) {
            mHeadHeight = Math.min(offset, height)
            mWaveHeight = Math.max(0, offset - height)

            val thisView = this
            thisView.postInvalidate()
        }

        if (isDragging || !mProgress.isRunning && !mFinished) {

            if (mState != RefreshState.Refreshing) {
                val originalDragPercent = 1f * offset / height

                val dragPercent = Math.min(1f, Math.abs(originalDragPercent))
                val adjustedPercent = Math.max(dragPercent - .4, 0.0).toFloat() * 5 / 3
                val extraOS = (Math.abs(offset) - height).toFloat()
                val tensionSlingshotPercent = Math.max(0f, Math.min(extraOS, height.toFloat() * 2) / height.toFloat())
                val tensionPercent = (tensionSlingshotPercent / 4 - Math.pow(
                        (tensionSlingshotPercent / 4).toDouble(), 2.0)).toFloat() * 2f
                val strokeStart = adjustedPercent * .8f
                mProgress.showArrow(true)
                mProgress.setStartEndTrim(0f, Math.min(MAX_PROGRESS_ANGLE, strokeStart))
                mProgress.setArrowScale(Math.min(1f, adjustedPercent))

                val rotation = (-0.25f + .4f * adjustedPercent + tensionPercent * 2) * .5f
                mProgress.setProgressRotation(rotation)
            }

            val circleView = mCircleView
            val targetY = (offset / 2 + mCircleDiameter / 2).toFloat()
            circleView.translationY = Math.min(offset.toFloat(), targetY)
            circleView.alpha = Math.min(1f, 4f * offset / mCircleDiameter)
        }
    }

    override fun onReleased(layout: RefreshLayout, height: Int, maxDragHeight: Int) {
        mProgress.start()
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        val circleView = mCircleView
        mState = newState
        when (newState) {
            RefreshState.None -> {
            }
            RefreshState.PullDownToRefresh -> {
                mFinished = false
                circleView.visibility = View.VISIBLE
                circleView.translationY = 0f
                circleView.scaleX = 1f
                circleView.scaleY = 1f
            }
            RefreshState.ReleaseToRefresh -> {
            }
            RefreshState.Refreshing -> {
            }
        }
    }

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        val circleView = mCircleView
        mProgress.stop()
        circleView.animate().scaleX(0f).scaleY(0f)
        mFinished = true
        return 0
    }

    /**
     * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
     */
    @Deprecated("请使用 {@link RefreshLayout#setPrimaryColorsId(int...)}")
    override fun setPrimaryColors(@ColorInt vararg colors: Int) {
        if (colors.size > 0) {
            mBezierPaint.color = colors[0]
        }
    }

    /**
     * 设置 ColorScheme
     * @param colors ColorScheme
     * @return MaterialHeader
     */
    fun setColorSchemeColors(@ColorInt vararg colors: Int): MaterialHeader {
        mProgress.setColorSchemeColors(*colors)
        return this
    }

    /**
     * 设置 ColorScheme
     * @param colorIds ColorSchemeResources
     * @return MaterialHeader
     */
    fun setColorSchemeResources(@ColorRes vararg colorIds: Int): MaterialHeader {
        val thisView = this
        val context = thisView.context
        val colors = IntArray(colorIds.size)
        for (i in colorIds.indices) {
            colors[i] = SmartUtil.getColor(context, colorIds[i])
        }
        return setColorSchemeColors(*colors)
    }

    /**
     * 设置大小尺寸
     * @param size One of DEFAULT, or LARGE.
     * @return MaterialHeader
     */
    fun setSize(size: Int): MaterialHeader {
        if (size != SIZE_LARGE && size != SIZE_DEFAULT) {
            return this
        }
        val thisView = this
        val metrics = thisView.resources.displayMetrics
        if (size == SIZE_LARGE) {
            mCircleDiameter = (CIRCLE_DIAMETER_LARGE * metrics.density).toInt()
        } else {
            mCircleDiameter = (CIRCLE_DIAMETER * metrics.density).toInt()
        }
        // force the bounds of the progress circle inside the circle view to
        // update by setting it to null before updating its size and then
        // re-setting it
        mCircleView.setImageDrawable(null)
        mProgress.updateSizes(size)
        mCircleView.setImageDrawable(mProgress)
        return this
    }

    /**
     * 是否显示贝塞尔图形
     * @param show 是否显示
     * @return MaterialHeader
     */
    fun setShowBezierWave(show: Boolean): MaterialHeader {
        this.mShowBezierWave = show
        return this
    }

    companion object {
        // Maps to ProgressBar.Large style
        val SIZE_LARGE = 0
        // Maps to ProgressBar default style
        val SIZE_DEFAULT = 1

        protected val CIRCLE_BG_LIGHT = -0x50506
        protected val MAX_PROGRESS_ANGLE = .8f
        @VisibleForTesting
        protected val CIRCLE_DIAMETER = 40
        @VisibleForTesting
        protected val CIRCLE_DIAMETER_LARGE = 56
    }
}
