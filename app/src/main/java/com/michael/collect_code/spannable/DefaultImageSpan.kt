package com.michael.collect_code.spannable

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.style.ReplacementSpan
import android.util.TypedValue
import android.widget.TextView

/**
 * loadAction  该方法中需要调用  setBitmap   来设置图片
 *
 *
 * Glide.with(textView)
.asBitmap()
.load(url)
.into(this)
 */
class DefaultImageSpan(val tv: TextView, val url: String, val w: Float, val h: Float,val loadAction:(String, TextView, DefaultImageSpan)->Unit={ _, _, _->}) : ReplacementSpan() {

    private var mWidth = 0
    private var mHeight = 0
    private var drawable: Drawable? = null

    init {
        mWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, tv.resources.displayMetrics).toInt()
        mHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, h, tv.resources.displayMetrics).toInt()
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        // 更新文本行高
        fm?.setTextLineHeight()
        return mWidth
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        drawable?.let {
            canvas.save()
            val fmPaint = paint.fontMetricsInt
            val fontHeight = fmPaint.descent - fmPaint.ascent
            val centerY = y + fmPaint.descent - fontHeight / 2
            val transY = centerY - mHeight / 2.0f
            canvas.translate(x, transY)

            it.draw(canvas)
            canvas.restore()
        }
        loadAction(url,tv,this)

    }


    /**
     * 显示下载下来的bitmap
     */
    fun setBitmap(bitmap: Bitmap) {
        drawable = BitmapDrawable(null, bitmap).apply {
            setBounds(0, 0, mWidth, mHeight)
        }
        tv.invalidate()
    }


    private fun Paint.FontMetricsInt.setTextLineHeight() {
        val fontHeight = descent - ascent
        val centerY = ascent + fontHeight / 2

        ascent = centerY - mHeight / 2
        descent = centerY + mHeight / 2

        top = ascent
        bottom = descent
    }


}