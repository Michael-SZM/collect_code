package com.michael.collect_code

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * 时间线效果
 */
class TimeLinelItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    private val leftWidth = context.dip(40)
    private val upHeight = 0
    private val downHeight = 0
    private val yOffset = context.dip(38)
    private val innerRadius = context.dip(3.5f)
    private val outerRadius = context.dip(5.5f)
    private val tmpPATH :Path = Path()

    private val top = PointF()
    private val center = PointF()
    private val bottom = PointF()

    private val innerCirclePaint by lazy {
        Paint().apply {
            color = ContextCompat.getColor(context, R.color.r25)
            style = Paint.Style.FILL
            isDither = true
        }
    }

    private val outerCirclePaint by lazy {
        Paint().apply {
            color = ContextCompat.getColor(context, R.color.r25_52)
            style = Paint.Style.FILL
            isDither = true
        }
    }


    private val linePaint by lazy {
        Paint().apply {
            color = ContextCompat.getColor(context, R.color.c27)
            style = Paint.Style.STROKE
            pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)
            strokeWidth = context.dip(0.3f).toFloat()
            isDither = true
        }
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount //当前recyclerView里有几个view

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child) //真实的position
            if (position == 0) continue

            center.x = (child.left / 2).toFloat()
            center.y = (child.top + yOffset).toFloat()

            top.x = center.x
            top.y = (child.top - upHeight).toFloat()

            bottom.x = center.x
            bottom.y = (child.bottom + downHeight).toFloat()

            if (position != 1) {
//                c.drawLine(top.x, top.y, center.x, center.y, linePaint)
                tmpPATH.reset()
                tmpPATH.moveTo(top.x, top.y)
                tmpPATH.lineTo(center.x, center.y)
                c.drawPath(tmpPATH,linePaint)
            }
            val itemCount = parent.adapter?.itemCount ?: 0
            if (itemCount != 0 && position == itemCount - 1) {
                bottom.y = parent.layoutManager?.height?.toFloat() ?: bottom.y
            }else{
//                c.drawLine(center.x, center.y, bottom.x, bottom.y, linePaint)
                tmpPATH.reset()
                tmpPATH.moveTo(center.x, center.y)
                tmpPATH.lineTo(bottom.x, bottom.y)
                c.drawPath(tmpPATH,linePaint)
            }
//            c.drawLine(center.x, center.y, bottom.x, bottom.y, linePaint)

            c.drawCircle(center.x, center.y, innerRadius.toFloat(), innerCirclePaint)
            c.drawCircle(center.x, center.y, outerRadius.toFloat(), outerCirclePaint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.set(leftWidth, upHeight, 0, downHeight)
        } else {
            super.getItemOffsets(outRect, view, parent, state)
        }
    }
}