package com.cardee.renter_browse_cars_map

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.cardee.R

class ClusterIconView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val counterAspectRatio = 3f
    private val iconBackgroundScaleRatio = .8f
    private val textAspectRation = .2f
    private val counterStrokeWidth = 3
    private val counterHorizontalShiftRatio = .07f
    private val counterVerticalShiftRatio = .1f

    private var counterCircleRadius: Float = 0f
    private var counterStrokedRadius: Float = 0f
    private var counterCenterX: Float = 0f
    private var counterCenterY: Float = 0f
    private var titleYPos: Float = 0f
    private val iconPaint: Paint = Paint()
    private val counterPaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val iconSrcRect: Rect = Rect()
    private val iconDstRect: RectF = RectF()
    private val iconBitmap: Bitmap
    private var title: String = ""

    init {
        val rawBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_car_marker)
        iconBitmap = Bitmap.createScaledBitmap(rawBitmap, 128, 128, false)
        rawBitmap.recycle()
        iconSrcRect.run {
            top = 0
            left = 0
            right = iconBitmap.width
            bottom = iconBitmap.height
        }
        iconPaint.run {
            isAntiAlias = true
            color = Color.WHITE
        }
        counterPaint.run {
            isAntiAlias = true
            color = Color.rgb(237, 89, 82)
        }
        textPaint.run {
            isAntiAlias = true
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
        calculateMeasures(width, height)
    }

    private fun calculateMeasures(width: Int, height: Int) {
        val fWidth = width.toFloat()
        val fHeight = height.toFloat()
        val base = Math.min(fWidth, fHeight)
        val centerX = fWidth / 2
        val centerY = fHeight / 2
        val iconRadius = base * iconBackgroundScaleRatio / 2
        counterCircleRadius = (base / counterAspectRatio) / 2
        counterStrokedRadius = counterCircleRadius + counterStrokeWidth
        textPaint.textSize = base * textAspectRation
        iconDstRect.run {
            top = centerY - iconRadius
            left = centerX - iconRadius
            right = centerX + iconRadius
            bottom = centerY + iconRadius
        }
        val hShift = base * counterHorizontalShiftRatio
        val vShift = base * counterVerticalShiftRatio
        counterCenterX = iconDstRect.right - counterCircleRadius + hShift
        counterCenterY = iconDstRect.top + vShift
        titleYPos = counterCenterY - ((textPaint.descent() + textPaint.ascent()) / 2)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            drawBitmap(iconBitmap, iconSrcRect, iconDstRect, iconPaint)
            drawCircle(counterCenterX, counterCenterY, counterStrokedRadius, iconPaint)
            drawCircle(counterCenterX, counterCenterY, counterCircleRadius, counterPaint)
            drawText(title, counterCenterX, titleYPos, textPaint)
        }
    }

    fun setTitle(title: String?) {
        this.title = title ?: ""
        invalidate()
    }
}
