package com.michael.collect_code.spannable

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.MaskFilter
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.*
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.IntDef
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import java.io.Serializable
import java.util.regex.Pattern

/**
 * 正则替换文本的需求不常见，暂时不支持
 * 针对DefaultImageSpan  和  IconTextSpan  在前置文字不满一行的情况下，后面的文字会换行的情况，可以通过设置  TextView的宽度为全屏来解决
 */
class SpannableHelper(defaultMsg: String = "") {

    val sb = SpannableStringBuilder(defaultMsg)

    /**
     * 接受一个正则表达式，并用给匹配到的内容添加富文本
     */
    fun setContent(cc: String, regx: String, context: Context,
                   bitmap: Bitmap): SpannableHelper {
        val pattern = Pattern.compile(regx)
        val matcher = pattern.matcher(cc)
        var n = 0
        while (matcher.find()){
            n++
        }
        var tmpNum = 0
        val parts = cc.split(regx)
        parts.forEach {
            if (tmpNum < n){
                opString(it).appendImage(context,bitmap)
                tmpNum++
            }
        }

        return this
    }


    /**
     * 接受一个正则表达式，并用给匹配到的内容添加富文本
     */
    fun setContent(cc: String, regx: String, drawable:Drawable,w:Int,h:Int): SpannableHelper {
        val pattern = Pattern.compile(regx)
        val matcher = pattern.matcher(cc)
        var n = 0
        while (matcher.find()){
            n++
        }
        var tmpNum = 0
        val parts = cc.split(regx)
        parts.forEach {
            if (tmpNum < n-1){
                opString(it).appendImage(drawable,w,h)
                tmpNum++
            }
        }

        return this
    }


    /**
     * cc  文本内容
     * padding   文字背景内边距
     * bgColor  背景颜色
     * textColor 文字颜色
     * cornerRadius  背景圆角半径
     * leftMargin  左外边距
     * rightMargin  右外边距
     */
    fun addLabelText(
        ctx: Context,
        cc: String,
        bgColor: Int = Color.BLUE,
        textColor: Int = Color.BLACK,
        padding: Float = 10f,
        cornerRadius: Float = 2f,
        aTextSize: Float = 13f,
        leftMargin: Float = 10f,
        rightMargin: Float = 10f,
        aTypeface: Typeface = Typeface.DEFAULT,
        cusTypeFaceStyle: Int = IconTextSpan.CusTypefaceStyleDef.NORMAL,
        action: (View) -> Unit = {}
    ): SpannableHelper {
        val stub = "stub"
        val start = sb.toString().length
        sb.append(stub)
        val end = sb.toString().length

        sb.setSpan(
            InnerClickSpan(
                false,
                action
            ), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        sb.setSpan(
            IconTextSpan(
                ctx,
                cc,
                bgColor,
                textColor,
                padding,
                cornerRadius,
                aTextSize,
                leftMargin,
                rightMargin,
                aTypeface,
                cusTypeFaceStyle
            ), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    /**
     * cc 文本内容
     * color 文本颜色
     * style 文本样式
     * clickConfig 文本点击配置,,需要tv.setMovementMethod(LinkMovementMethod.getInstance())
     * rSize  文本相对大小
     * scaleX 文本x轴缩放比例
     * strikethrough 文本是否使用删除线
     * subscript  文本是否是脚注样式
     * superscript 文本是否使用上标样式
     * url 文本点击跳转的url
     * quote  文本添加引用样式的颜色
     * maskFilter 给文本添加滤镜样式  系统默认提供  BlurMaskFilter  模糊滤镜  ；EmbossMaskFilter 浮雕效果滤镜
     * absoluteSize 给文本设置绝对大小
     * backgroundColorConfig 给文本设置背景色
     * bulletConfig 给文本添加标签的原点效果
     * typeface
     * drawableMarginConfig  添加图片
     * iconMarginConfig 添加图片
     */
    fun opString(
        cc: String,
        color: Int = -1,
        @TypefaceStyleDef style: Int = TypefaceStyleDef.NONE,
        clickConfig: ClickConfig? = null,
        rSize: Float = -1f,
        scaleX: Float = -1f,
        strikethrough: Boolean = false,
        subscript: Boolean = false,
        superscript: Boolean = false,
        urlConfig: UrlConfig? = null,
        quote: QuoteConfig = QuoteConfig(),
        maskFilter: MaskFilter? = null,
        leadingMarginConfig: LeadingMarginConfig? = null,
        absoluteSizeConfig: AbsoluteSizeConfig? = null,
        backgroundColorConfig: BackgroundColorConfig? = null,
        bulletConfig: BulletConfig? = null,
        drawableMarginConfig: DrawableMarginConfig? = null,
        iconMarginConfig: IconMarginConfig? = null,
        typefaceConfig: TypefaceConfig? = null
    ): SpannableHelper {
        val start = sb.toString().length
        val end = start + cc.length
        return opString(
            cc,
            start,
            end,
            color,
            style,
            clickConfig,
            rSize,
            scaleX,
            strikethrough,
            subscript,
            superscript,
            urlConfig,
            quote,
            maskFilter,
            leadingMarginConfig,
            absoluteSizeConfig,
            backgroundColorConfig,
            bulletConfig,
            drawableMarginConfig,
            iconMarginConfig,
            typefaceConfig
        )
    }


    fun opString(
        cc: String,
        start: Int,
        end: Int,
        color: Int = -1,
        @TypefaceStyleDef style: Int = TypefaceStyleDef.NONE,
        clickConfig: ClickConfig? = null,
        rSize: Float = -1f,
        scaleX: Float = -1f,
        strikethrough: Boolean = false,
        subscript: Boolean = false,
        superscript: Boolean = false,
        urlConfig: UrlConfig? = null,
        quote: QuoteConfig = QuoteConfig(),
        maskFilter: MaskFilter? = null,
        leadingMarginConfig: LeadingMarginConfig? = null,
        absoluteSizeConfig: AbsoluteSizeConfig? = null,
        backgroundColorConfig: BackgroundColorConfig? = null,
        bulletConfig: BulletConfig? = null,
        drawableMarginConfig: DrawableMarginConfig? = null,
        iconMarginConfig: IconMarginConfig? = null,
        typefaceConfig: TypefaceConfig? = null
    ): SpannableHelper {
        sb.append(cc)
        if (color != -1) {
            sb.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (style != TypefaceStyleDef.NONE) {
            sb.setSpan(StyleSpan(style), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (clickConfig !== null && clickConfig.valid) {
            sb.setSpan(
                InnerClickSpan(
                    clickConfig.showUnderLine,
                    clickConfig.action
                ), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (rSize != -1f) {
            sb.setSpan(RelativeSizeSpan(rSize), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (scaleX != -1f) {
            sb.setSpan(ScaleXSpan(scaleX), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (strikethrough) {
            sb.setSpan(StrikethroughSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (subscript) {
            sb.setSpan(SubscriptSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (superscript) {
            sb.setSpan(SuperscriptSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (urlConfig != null && urlConfig.valid) {
            sb.setSpan(UrlSpan(urlConfig.url,urlConfig.color,urlConfig.showUnderLine), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (quote.valid) {
            sb.setSpan(QuoteSpan(quote.color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (maskFilter != null) {
            sb.setSpan(MaskFilterSpan(maskFilter), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (leadingMarginConfig != null && leadingMarginConfig.valid) {
            sb.setSpan(
                LeadingMarginSpan.Standard(
                    leadingMarginConfig.arg0,
                    leadingMarginConfig.arg1
                ), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (absoluteSizeConfig != null && absoluteSizeConfig.valid) {
            sb.setSpan(
                AbsoluteSizeSpan(absoluteSizeConfig.size, absoluteSizeConfig.dp),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (backgroundColorConfig != null && backgroundColorConfig.valid) {
            sb.setSpan(
                BackgroundColorSpan(backgroundColorConfig.color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (bulletConfig != null && bulletConfig.valid) {
            sb.setSpan(
                BulletSpan(bulletConfig.width, bulletConfig.color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (typefaceConfig != null && typefaceConfig.valid) {
            sb.setSpan(
                TypefaceSpan(typefaceConfig.typeFace), start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        if (drawableMarginConfig != null && drawableMarginConfig.valid) {
            sb.setSpan(
                DrawableMarginSpan(drawableMarginConfig.d, drawableMarginConfig.pad),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (iconMarginConfig != null && iconMarginConfig.valid) {
            sb.setSpan(
                IconMarginSpan(iconMarginConfig.bitmap, iconMarginConfig.pad),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return this
    }


    fun appendImage(
        context: Context,
        bitmap: Bitmap,
        verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM
    ): SpannableHelper {
        val stub = "stub"
        val start = sb.toString().length
        sb.append(stub)
        val end = sb.toString().length
        sb.setSpan(
            ImageSpan(context, bitmap, verticalAlignment),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    fun appendImage(
        drawable: Drawable,
        width: Int,
        hight: Int,
        verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM
    ): SpannableHelper {
        val stub = "stub"
        val start = sb.toString().length
        sb.append(stub)
        val end = sb.toString().length
        drawable.setBounds(0, 0, width, hight)
        sb.setSpan(
            ImageSpan(drawable, verticalAlignment),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    fun appendImage(
        context: Context,
        uri: Uri,
        verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM
    ): SpannableHelper {
        val stub = "stub"
        val start = sb.toString().length
        sb.append(stub)
        val end = sb.toString().length
        sb.setSpan(
            ImageSpan(context, uri, verticalAlignment),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }


    fun build(): SpannableStringBuilder {
        return sb
    }

    /**
     * loadAction (tv,url,defaultImageSpan) 中需要调用DefaultImageSpan的setBitmap方法
     *
     *
    Glide.with(tv)
    .asBitmap()
    .load(url)
    .into(CusTarget(tv,defaultImageSpan))
     *
     *
     * width  dp
     * hight  dp
     *
     */
    fun appendImage(
        url: String,
        width: Float,
        hight: Float,
        tv: TextView,
        loadAction: (String, TextView, DefaultImageSpan) -> Unit = { _, _, _ -> }
    ): SpannableHelper {
        val stub = "stub"
        val start = sb.toString().length
        sb.append(stub)
        val end = sb.toString().length
        sb.setSpan(
            DefaultImageSpan(tv, url, width, hight, loadAction),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }


    data class ClickConfig(
        var showUnderLine: Boolean = false,
        var action: (View) -> Unit = {},
        var valid: Boolean = true
    ) : Serializable


    data class QuoteConfig(
        val color: Int = Color.BLUE,
        val valid: Boolean = true
    ) : Serializable


    data class AbsoluteSizeConfig(
        val size: Int = 20,
        val dp: Boolean = true,
        val valid: Boolean = true
    ) : Serializable

    data class BackgroundColorConfig(
        val color: Int = Color.BLUE,
        val valid: Boolean = true
    ) : Serializable


    data class BulletConfig(
        val width: Int = 0,
        val color: Int = Color.BLACK,
        val valid: Boolean = true
    ) : Serializable


    /**
     * typeFace :The font family for this typeface.  Examples include
     *               "monospace", "serif", and "sans-serif"
     */
    data class TypefaceConfig(
        val typeFace: String = "serif",
        val valid: Boolean = true
    ) : Serializable

    data class DrawableMarginConfig(
        val d: Drawable = ColorDrawable(Color.BLUE),
        val pad: Int = 0,
        val valid: Boolean = true
    ) : Serializable

    data class IconMarginConfig(
        val bitmap: Bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.RGB_565),
        val pad: Int = 0,
        val valid: Boolean = true
    ) : Serializable


    /**
     * 文本缩进的样式,,参数arg0，首行缩进的像素，arg1，剩余行缩进的像素
     */
    data class LeadingMarginConfig(
        val arg0: Int = 0,
        val arg1: Int = 0,
        val valid: Boolean = false
    ) : Serializable

    inner class InnerClickSpan(
        val showUnderLine: Boolean = false,
        val action: (View) -> Unit = {}
    ) : ClickableSpan() {

        override fun onClick(widget: View) {
            action(widget)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = showUnderLine

        }

    }

    data class UrlConfig(
        val url: String = "",
        val color: Int = Color.BLUE,
        val showUnderLine: Boolean = true,
        val valid: Boolean = true
    ):Serializable


    fun dp2px(ctx: Context, num: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            num,
            ctx.resources.displayMetrics
        )
    }

    fun sp2px(ctx: Context, num: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            num,
            ctx.resources.displayMetrics
        )
    }


    class CusTarget(view: View, val defaultImageSpan: DefaultImageSpan) :
        CustomViewTarget<View, Bitmap>(view) {

        override fun onLoadFailed(errorDrawable: Drawable?) {
        }

        override fun onResourceCleared(placeholder: Drawable?) {
        }

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            resource.let { defaultImageSpan.setBitmap(it) }
        }

    }


    @IntDef(value = [TypefaceStyleDef.NORMAL, TypefaceStyleDef.BOLD, TypefaceStyleDef.ITALIC, TypefaceStyleDef.BOLD_ITALIC, TypefaceStyleDef.NONE])
    @Retention(AnnotationRetention.SOURCE)
    internal annotation class TypefaceStyleDef {
        companion object {
            const val NORMAL = Typeface.NORMAL
            const val BOLD = Typeface.BOLD
            const val ITALIC = Typeface.ITALIC
            const val BOLD_ITALIC = Typeface.BOLD_ITALIC
            const val NONE = -1
        }
    }

}