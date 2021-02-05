package com.michael.collect_code.spannable

import android.graphics.Color
import android.os.Parcel
import android.text.TextPaint
import android.text.style.ForegroundColorSpan

class MutableForegroundColorSpanKt(var mAlpha:Int,var mForegroundColor:Int) : ForegroundColorSpan(mForegroundColor) {

    constructor(parcel: Parcel):this(parcel.readInt(),parcel.readInt())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeInt(mForegroundColor)
        dest.writeFloat(mAlpha.toFloat())
    }

    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.color = foregroundColor
    }

    override fun getForegroundColor(): Int {
        return Color.argb(mAlpha, Color.red(mForegroundColor), Color.green(mForegroundColor), Color.blue(mForegroundColor))
    }

}