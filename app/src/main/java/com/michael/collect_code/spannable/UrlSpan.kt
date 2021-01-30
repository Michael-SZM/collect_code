package com.michael.collect_code.spannable

import android.graphics.Color
import android.text.TextPaint
import android.text.style.URLSpan

class UrlSpan(url:String,val color:Int = Color.BLUE,val showUnderLine:Boolean = true) : URLSpan(url) {

    override fun updateDrawState(ds: TextPaint) {
        if (showUnderLine){
            ds.color = color
            ds.isUnderlineText = true
        }else{
            ds.color = color
            ds.isUnderlineText = false
        }

    }

}