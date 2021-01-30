package com.michael.collect_code.spannable

import android.content.Context
import android.graphics.*
import android.graphics.Paint.FAKE_BOLD_TEXT_FLAG
import android.graphics.Paint.Style.FILL
import android.os.Build
import android.text.TextPaint
import android.text.style.ReplacementSpan
import android.util.TypedValue
import androidx.annotation.IntDef
import com.michael.collect_code.spannable.IconTextSpan.CusTypefaceStyleDef.Companion.BOLD
import com.michael.collect_code.spannable.IconTextSpan.CusTypefaceStyleDef.Companion.ITALIC
import com.michael.collect_code.spannable.IconTextSpan.CusTypefaceStyleDef.Companion.NORMAL


/**
 * padding   内边距
 * cornerRadius   背景圆角
 */
class IconTextSpan(
    private val ctx: Context,
    private val cc: String = "label",
    private val bgColor: Int = Color.BLUE,
    private val textColor: Int = Color.BLACK,
    private val padding: Float = 10f,
    private val cornerRadius: Float = 2f,
    private val aTextSize: Float = 13f,
    private val leftMargin:Float = 10f,
    private val rightMargin:Float = 10f,
    private val aTypeface:Typeface = Typeface.DEFAULT,
    @CusTypefaceStyleDef private val cusTypeFaceStyle: Int = CusTypefaceStyleDef.NORMAL
) : ReplacementSpan() {

    private var mBgHeight //Icon背景高度
            = 0f
    private var mBgWidth //Icon背景宽度
            = 0f
    private var mRadius //Icon圆角半径
            = 0f
    private var mRightMargin //右边距
            = 0f
    private var mLeftMargin //左边距
            = 0f
    private var mTextSize //文字大小
            = 0f

    private var mBgPaint //icon背景画笔
            : Paint? = null
    private var mTextPaint //icon文字画笔
            : Paint? = null

    private var mTextRectWidth = 0f
    private var textLeftOffset = 0f

    init {
        initPaint()
        //初始化默认数值
        initDefaultValue(ctx)
        //计算背景的宽度
        this.mBgWidth = caculateBgWidth(cc)

        textLeftOffset = (mBgWidth - mTextRectWidth) * 0.5f

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
        return (mBgWidth + mRightMargin +mLeftMargin).toInt()
    }

    private fun Paint.FontMetricsInt.setTextLineHeight() {
        val fontHeight = descent - ascent
        val centerY = ascent + fontHeight / 2

        ascent = ((centerY - mBgHeight / 2)+0.5).toInt()
        descent = ((centerY + mBgHeight / 2)+0.5f).toInt()

        top = ascent
        bottom = descent
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {

        canvas.save()

        val fmPaint = paint.fontMetricsInt
        val fontHeight = fmPaint.descent - fmPaint.ascent
        val centerY = y + fmPaint.descent - fontHeight / 2
        val transY = centerY - mBgHeight / 2.0f
        canvas.translate(x, transY)


        val left =mLeftMargin
        val tmpTop = 0f
        mBgPaint?.apply {
            val bgRect = RectF(left, tmpTop, left + mBgWidth, tmpTop + mBgHeight)
            canvas.drawRoundRect(bgRect, mRadius, mRadius, mBgPaint!!)
        }

        //把字画在背景中间
        mTextPaint?.apply {
            canvas.drawText(
                cc,
                left + textLeftOffset - padding ,
                (descent() - ascent()) * 0.5f +((descent() - ascent())* 0.5f - descent()),
                this
            )
        }

        canvas.restore()

    }


    /**
     * 初始化默认数值
     *
     * @param context
     */
    private fun initDefaultValue(
        context: Context
    ) {
        val mContext = context.applicationContext
        mTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            aTextSize,
            mContext.resources.displayMetrics
        )
        mRightMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            rightMargin,
            mContext.resources.displayMetrics
        )
        mLeftMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            leftMargin,
            mContext.resources.displayMetrics
        )
        mRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            cornerRadius,
            mContext.resources.displayMetrics
        )

    }


    /**
     * 计算icon背景宽度
     *
     * @param text icon内文字
     */
    private fun caculateBgWidth(text: String): Float {
        val textRect = Rect()
        mTextPaint?.textSize = mTextSize
        mTextPaint?.getTextBounds(text, 0, text.length, textRect)
        mBgHeight = textRect.height().toFloat() + padding * 2
        mTextRectWidth = textRect.width().toFloat()
        return if (text.length > 1) {
            //多字，宽度=文字宽度+padding
            val padding = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                padding,
                ctx.resources.displayMetrics
            )
            textRect.width() + padding * 2
        } else {
            //单字，宽高一致为正方形
            mBgHeight
        }
    }

    /**
     * 初始化画笔
     */
    private fun initPaint() {
        //初始化背景画笔
        mBgPaint = Paint()
        mBgPaint?.apply {
            color = bgColor
            style = FILL
            isAntiAlias = true
        }


        when(cusTypeFaceStyle){
            NORMAL->{
                mTextPaint = TextPaint()
            }
            BOLD->{
                mTextPaint = TextPaint(FAKE_BOLD_TEXT_FLAG)
            }
            ITALIC->{
                mTextPaint = TextPaint()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mTextPaint?.fontVariationSettings = "'slnt' 20, 'ital' 1"
                }
            }
        }

        //初始化文字画笔

        mTextPaint?.apply {
            color = textColor
            typeface = aTypeface
//            strokeWidth = aStrokeWidth
            textSize = mTextSize
            isAntiAlias = true
        }
    }

    @IntDef(value = [CusTypefaceStyleDef.NORMAL, CusTypefaceStyleDef.BOLD, CusTypefaceStyleDef.ITALIC])
    @Retention(AnnotationRetention.SOURCE)
    internal annotation class CusTypefaceStyleDef {
        companion object {
            const val NORMAL = Typeface.NORMAL
            const val BOLD = Typeface.BOLD
            const val ITALIC = Typeface.ITALIC
        }
    }

}