package com.michael.collect_code.spannable

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.Log
import android.util.Property
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.michael.collect_code.R


class LatexActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latex)

        findViewById<TextView>(R.id.latex01).apply {
            movementMethod = LinkMovementMethod.getInstance()
            val msg = SpannableHelper("默认值")
                .opString("第一段文本，世界美好", color = Color.RED).opString("\n")
                .opString(
                    "第二段文本，岁月静好",
                    color = Color.BLUE,
                    absoluteSizeConfig = SpannableHelper.AbsoluteSizeConfig(25, true)
                )
                .opString(
                    "第三段文本，风和人和",
                    color = Color.RED,
                    backgroundColorConfig = SpannableHelper.BackgroundColorConfig(
                        Color.YELLOW
                    )
                )
                .opString(
                    "第四段文本，世界美好",
                    color = Color.BLACK,
                    clickConfig = SpannableHelper.ClickConfig(true, action = {
                        Toast.makeText(this@LatexActivity, "拒绝不美好的对象", Toast.LENGTH_SHORT).show()
                    })
                )
                .opString(
                    "第五段文本，世界美好,隐藏下划线",
                    clickConfig = SpannableHelper.ClickConfig(false, action = {
                        Toast.makeText(this@LatexActivity, "拒绝不美好的对象", Toast.LENGTH_SHORT).show()
                    }),
                    rSize = 1.1f
                )
                .opString(
                    "第六段文本，模糊 模糊 模糊", color = Color.GREEN, maskFilter = BlurMaskFilter(
                        1.5f,
                        BlurMaskFilter.Blur.SOLID
                    ), style = Typeface.BOLD, rSize = 1.1f
                )
//                .opString("  第七段文本，世界美好",color = Color.RED,maskFilter = EmbossMaskFilter(floatArrayOf(1f,1f,1f),0.4f, 6f, 3.5f))
//                .opString("\n")
                .opString("上标", superscript = true, color = Color.GRAY, rSize = 0.5f)
                .opString("演示脚注").opString("脚注", subscript = true, rSize = 0.5f)
                .opString("删除线", strikethrough = true)
                .opString("字体", typefaceConfig = SpannableHelper.TypefaceConfig("monospace"))
                .appendImage(resources.getDrawable(R.drawable.icon_bulb), 40, 40)
                .opString(
                    "我是url演示",
                    urlConfig = SpannableHelper.UrlConfig("http:www.baidu.com", Color.RED, false),
                    rSize = 1.2f,
                    style = SpannableHelper.TypefaceStyleDef.BOLD
                )
                .build()
            text = msg
        }


        findViewById<TextView>(R.id.latex02).apply {
            val msg = SpannableHelper()
                .opString("条目一\n你好kkk\nhahh", quote = SpannableHelper.QuoteConfig(Color.RED))
                .build()
            text = msg
        }

        findViewById<TextView>(R.id.latex03).apply {
            val msg = SpannableHelper()
                .opString("条目一", bulletConfig = SpannableHelper.BulletConfig(20, Color.RED))
                .build()
            text = msg
        }

        findViewById<TextView>(R.id.latex04).apply {
            val drawable = resources.getDrawable(R.drawable.icon_bulb)
//            val msg = SpannableHelper("mmmm")
            val msg = SpannableHelper()
                .opString(
                    "xxx",
                    drawableMarginConfig = SpannableHelper.DrawableMarginConfig(drawable, 10)
                )
                .build()
            text = msg
        }

        findViewById<TextView>(R.id.latex05).apply {
            val drawable = resources.getDrawable(R.drawable.icon_bulb)
//            val msg = SpannableHelper("mmmm")
            val msg = SpannableHelper()
                .opString(
                    "xxxxx",
                    absoluteSizeConfig = SpannableHelper.AbsoluteSizeConfig(25, true),
                    iconMarginConfig = SpannableHelper.IconMarginConfig(
                        BitmapFactory.decodeResource(resources, R.drawable.icon_bulb),
                        3
                    )
                )
                .build()
            text = msg
        }

        findViewById<TextView>(R.id.latex06).apply {
            val drawable = resources.getDrawable(R.drawable.icon_bulb)
            val msg = SpannableHelper("mmmm")
//            val msg = SpannableHelper()
                .appendImage(drawable, 40, 40)
                .build()
            text = msg
        }

        findViewById<TextView>(R.id.latex07).apply {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_bulb)
            val msg = SpannableHelper("mmmm")
//            val msg = SpannableHelper()
                .appendImage(this@LatexActivity, bitmap)
                .build()
            text = msg
        }
//        val uri: Uri = Uri.parse("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3350497021,3015906272&fm=26&gp=0.jpg")
        findViewById<TextView>(R.id.latex08).apply {
            val msg = SpannableHelper("mmmm")
//                .opString("xxxxxxxxxxxx KLLLNKSLKKKKKKKKKKKKKKKKKKSSSSSSSSS ksjnlk是内裤男老师那里的可能拉深南电路哪里是你的拉链深南电路")
                .opString("xxxxxxxxxxxx  ksjnlk是内裤男老师那里的可能拉深南电路哪里是你的拉链深南电路")
                .appendImage(
                    "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3350497021,3015906272&fm=26&gp=0.jpg",
                    60F,
                    60F,
                    this,
                    loadAction = { url, tv, defaultImageSpan ->
                        Glide.with(tv)
                            .asBitmap()
                            .load(url)
                            .into(SpannableHelper.CusTarget(tv, defaultImageSpan))
                    }
                )
                .opString("xxxxxxxxxxxx")
                .build()
            text = msg
        }

        findViewById<TextView>(R.id.latex09).apply {
            movementMethod = LinkMovementMethod.getInstance()
            val msg = SpannableHelper("mmmm")
                .addLabelText(
                    this@LatexActivity,
                    "标签一",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .addLabelText(
                    this@LatexActivity,
                    "标",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .addLabelText(
                    this@LatexActivity,
                    "签",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .opString("skjbkjkjnknjk 看接口尽可能看接口看见你看见你空间即可可能就看你能看见看见看见看见你就能看见你尽快将可能就可能就能看空间")
                .addLabelText(
                    this@LatexActivity,
                    "标",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .appendImage(
                    "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3350497021,3015906272&fm=26&gp=0.jpg",
                    60F,
                    60F,
                    this,
                    loadAction = { url, tv, defaultImageSpan ->
                        Glide.with(tv)
                            .asBitmap()
                            .load(url)
                            .into(SpannableHelper.CusTarget(tv, defaultImageSpan))
                    }
                )
//                .opString("xxxxxxxxxxxx")
                .addLabelText(
                    this@LatexActivity,
                    "元",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    leftMargin = 0f,
                    rightMargin = 0f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .build()
            text = msg
        }

        findViewById<TextView>(R.id.latex10).apply {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_bulb)
            val msg = SpannableHelper("mmmm")
                .setContent("__看时间内卡死看上的空间啊科技空间大__nnjknkkkjjk_加数据看接口","_",this@LatexActivity,bitmap)
                .build()
            text = msg
        }

        findViewById<TextView>(R.id.latex11).apply {
            val msg = SpannableHelper("mmmm")
//                .opString("xxxxxxxxxxxx KLLLNKSLKKKKKKKKKKKKKKKKKKSSSSSSSSS ksjnlk是内裤男老师那里的可能拉深南电路哪里是你的拉链深南电路")
//                .opString("xxxxxxxxxxxx  ksjnlk是内裤男老师那里的可能拉深南电路哪里是你的拉链深南电路")
                .opString("xxxxxxxxxxxx ")
//                .appendImage(
//                    "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3350497021,3015906272&fm=26&gp=0.jpg",
//                    60F,
//                    60F,
//                    this,
//                    loadAction = { url, tv, defaultImageSpan ->
//                        Glide.with(tv)
//                            .asBitmap()
//                            .load(url)
//                            .into(SpannableHelper.CusTarget(tv, defaultImageSpan))
//                    }
//                )
                .addLabelText(
                    this@LatexActivity,
                    "标",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .opString("xxxxxxxxxxxx")
                .build()
            text = msg
        }


        findViewById<TextView>(R.id.latex12).apply {
            setOnClickListener {
                val span = SpannableHelper("默认值").opString("第一段文本",color = Color.BLUE).build()
                Toast.makeText(this@LatexActivity,"click",Toast.LENGTH_SHORT).show()
                animateColorSpan(this,span,"动态添加的文本")
            }
        }

        findViewById<TextView>(R.id.latex13).apply {
            setOnClickListener {
                val spanHelper = SpannableHelper("默认值").opString("第一段文本",color = Color.BLUE)
                Toast.makeText(this@LatexActivity,"click",Toast.LENGTH_SHORT).show()
                animateColorSpan2(this,spanHelper,"动态添加的文本")
            }
        }

        findViewById<TextView>(R.id.latex14).apply {
            setOnClickListener {
                val spanHelper = SpannableHelper("默认值").opString("第一段文本",color = Color.BLUE)
                Toast.makeText(this@LatexActivity,"click",Toast.LENGTH_SHORT).show()
                animateFireworks(this,spanHelper.build(),"一起来看流星雨")
            }
        }

        findViewById<TextView>(R.id.latex15).apply {
            setOnClickListener {
                val spanHelper = SpannableHelper("默认值").opString("第一段文本",color = Color.BLUE)
                Toast.makeText(this@LatexActivity,"click",Toast.LENGTH_SHORT).show()
                animateTypeWriter(this,spanHelper.build(),"这是一段很长的文本书写。书写中。。。奋笔疾书中.....这是一段很长的文本书写。书写中。。。奋笔疾书中....." +
                        "这是一段很长的文本书写。书写中。。。奋笔疾书中.....这是一段很长的文本书写。书写中。。。奋笔疾书中.....这是一段很长的文本书写。书写中。。。奋笔疾书中....." +
                        "这是一段很长的文本书写。书写中。。。奋笔疾书中.....这是一段很长的文本书写。书写中。。。奋笔疾书中.....这是一段很长的文本书写。书写中。。。奋笔疾书中....." +
                        "这是一段很长的文本书写。书写中。。。奋笔疾书中.....这是一段很长的文本书写。书写中。。。奋笔疾书中.....这是一段很长的文本书写。书写中。。。奋笔疾书中....." +
                        "这是一段很长的文本书写。书写中。。。奋笔疾书中.....这是一段很长的文本书写。书写中。。。奋笔疾书中.....这是一段很长的文本书写。书写中。。。奋笔疾书中.....")
            }
        }

    }


    private fun animateColorSpan(textView: TextView,tSpan:SpannableStringBuilder,cc:String) {
        val span = MutableForegroundColorSpan(255, Color.BLUE)
        val start = tSpan.length
        tSpan.append(cc)
        val end = tSpan.length
        tSpan.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY, Color.BLACK, Color.RED)
        objectAnimator.setEvaluator(ArgbEvaluator())
        objectAnimator.addUpdateListener { //refresh
            textView.text = tSpan
        }
        val mSmoothInterpolator = AccelerateDecelerateInterpolator()
        objectAnimator.interpolator = mSmoothInterpolator
        objectAnimator.duration = 600
        objectAnimator.start()
    }

    private fun animateColorSpan2(textView: TextView,tSpanHelper:SpannableHelper,cc:String) {
        val span = MutableForegroundColorSpanKt(255, Color.BLUE)
        val tSpan = tSpanHelper.opString(cc,cusSpan = span).build()
        val objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY2, Color.BLACK, Color.RED)
        objectAnimator.setEvaluator(ArgbEvaluator())
        objectAnimator.addUpdateListener { //refresh
            textView.text = tSpan
        }
        val mSmoothInterpolator = AccelerateDecelerateInterpolator()
        objectAnimator.interpolator = mSmoothInterpolator
        objectAnimator.duration = 600
        objectAnimator.start()
    }


    private val MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY: Property<MutableForegroundColorSpan, Int> = object : Property<MutableForegroundColorSpan, Int>(Int::class.java, "MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY") {
        override operator fun set(alphaForegroundColorSpanGroup: MutableForegroundColorSpan, value: Int) {
            alphaForegroundColorSpanGroup.foregroundColor = value
        }

        override operator fun get(span: MutableForegroundColorSpan): Int {
            return span.foregroundColor
        }
    }

    private val MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY2: Property<MutableForegroundColorSpanKt, Int> = object : Property<MutableForegroundColorSpanKt, Int>(Int::class.java, "MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY") {
        override operator fun set(alphaForegroundColorSpanGroup: MutableForegroundColorSpanKt, value: Int) {
            alphaForegroundColorSpanGroup.mForegroundColor = value
        }

        override operator fun get(span: MutableForegroundColorSpanKt): Int {
            return span.foregroundColor
        }
    }


    // ----------------------------------------------------------------------------

    private fun animateFireworks(textView: TextView,spannableStringBuilder: SpannableStringBuilder,cc: String) {
        var tmpSpan = SpannableStringBuilder()
        val spanGroup: FireworksSpanGroup = buildFireworksSpanGroup(cc,0, cc.length - 1){
            tmpSpan.append(it)
        }
        val showSpan = spannableStringBuilder.append(tmpSpan)
        val objectAnimator = ObjectAnimator.ofFloat(spanGroup, FIREWORKS_GROUP_PROGRESS_PROPERTY, 0.0f, 1.0f)
        objectAnimator.addUpdateListener { //refresh
            textView.text = showSpan
        }
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.duration = 3000
        objectAnimator.start()
    }

    private fun buildFireworksSpanGroup(cc :String ,start: Int, end: Int,resBlock:(SpannableStringBuilder)->Unit={}): FireworksSpanGroup {
        val group = FireworksSpanGroup()
        val tmpSpan = SpannableStringBuilder(cc)
        for (index in start..end) {
            val span = MutableForegroundColorSpan(0, Color.BLUE)
            group.addSpan(span)
            tmpSpan.setSpan(span, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        group.init(true)
        resBlock(tmpSpan)
        return group
    }

    private val FIREWORKS_GROUP_PROGRESS_PROPERTY: Property<FireworksSpanGroup, Float> = object : Property<FireworksSpanGroup, Float>(Float::class.java, "FIREWORKS_GROUP_PROGRESS_PROPERTY") {
        override fun set(spanGroup: FireworksSpanGroup, value: Float) {
            spanGroup.setProgress(value)
        }

        override fun get(spanGroup: FireworksSpanGroup): Float {
            return spanGroup.mProgress
        }
    }


    private class FireworksSpanGroup {
        var mProgress = 0f
        val mSpans: ArrayList<MutableForegroundColorSpan> = ArrayList()
        fun addSpan(span: MutableForegroundColorSpan) {
            span.setAlpha(0)
            mSpans.add(span)
        }

        fun init(seri:Boolean = true) {
            if (!seri){
                mSpans.shuffle()
            }
        }

        fun setProgress(progress:Float) {
            mProgress = progress
            val size: Int = mSpans.size
            var total = 1.0f * size * progress
            if (DEBUG) Log.d(TAG, "progress $progress * 1.0f * size => $total")
            for (index in 0 until size) {
                val span = mSpans[index]
                if (total >= 1.0f) {
                    Log.d("szm--", "$progress")
                    span.setAlpha(255)
                    total -= 1.0f
                } else {
                    span.setAlpha((total * 255).toInt())
                    total = 0.0f
                }
            }
        }

        companion object {
            private const val DEBUG = true
            private const val TAG = "FireworksSpanGroup"
        }

    }



    // ---------------------------------------------typewriter-----------------------------------

    private fun animateTypeWriter(textView: TextView,spannableStringBuilder: SpannableStringBuilder,cc: String) {
        var tmpSpan = SpannableStringBuilder()
        val spanGroup: TypeWriterSpanGroup = buildTypeWriterSpanGroup(cc,0, cc.length - 1){
            tmpSpan.append(it)
        }
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(spanGroup, TYPE_WRITER_GROUP_ALPHA_PROPERTY, 0.0f, 1.0f)
        val showSpan = spannableStringBuilder.append(tmpSpan)
        objectAnimator.addUpdateListener { //refresh
            textView.text = showSpan
        }

        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.duration = 5000
        objectAnimator.start()
    }


    private fun buildTypeWriterSpanGroup(cc: String,start: Int, end: Int,resBlock:(SpannableStringBuilder)->Unit={}): TypeWriterSpanGroup {
        val group = TypeWriterSpanGroup(0f)
        val tmpSpan = SpannableStringBuilder(cc)
        for (index in start..end) {
            val span = MutableForegroundColorSpan(0, Color.BLACK)
            group.addSpan(span)
            tmpSpan.setSpan(span, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        resBlock(tmpSpan)
        return group
    }


    private val TYPE_WRITER_GROUP_ALPHA_PROPERTY: Property<TypeWriterSpanGroup, Float> = object : Property<TypeWriterSpanGroup, Float>(Float::class.java, "TYPE_WRITER_GROUP_ALPHA_PROPERTY") {
        override fun set(spanGroup: TypeWriterSpanGroup, value: Float) {
            spanGroup.setAlpha(value)
        }

        override fun get(spanGroup: TypeWriterSpanGroup): Float {
            return spanGroup.mAlpha
        }
    }


    private class TypeWriterSpanGroup (var mAlpha: Float) {
        private val mSpans: ArrayList<MutableForegroundColorSpan> = ArrayList()
        fun addSpan(span: MutableForegroundColorSpan) {
            span.setAlpha((mAlpha * 255).toInt())
            mSpans.add(span)
        }


        fun setAlpha(alpha:Float) {
            mAlpha = alpha
            val size: Int = mSpans.size
            var total = 1.0f * size * alpha
            if (DEBUG) Log.d(TAG, "alpha $alpha * 1.0f * size => $total")
            for (index in 0 until size) {
                val span = mSpans[index]
                if (total >= 1.0f) {
                    span.setAlpha(255)
                    total -= 1.0f
                } else {
                    span.setAlpha((total * 255).toInt())
                    total = 0.0f
                }
                if (DEBUG) Log.d(TAG, "alpha span($index) => $alpha")
            }
        }

        companion object {
            private const val DEBUG = false
            private const val TAG = "TypeWriterSpanGroup"
        }

    }

}