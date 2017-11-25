package ru.nobird.junction.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ru.nobird.junction.data.PlotBuffer

class PlotView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var baselineY = 0f
    private var mult = 1f

    val data = PlotBuffer(10000)

    init {
        for (i in 0..10000) {
            data.add(Math.abs(Math.cos(i.toDouble() / 250)).toFloat())
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF58D088.toInt()
    }

    override fun onDraw(canvas: Canvas) {
        val dx = width.toFloat() / data.size
        mult = height.toFloat()
        for (i in 1 .. data.size) {
            canvas.drawLine(dx * (i - 1), mult - data[i - 1] * mult, dx * i, mult - data[i] * mult, paint)
        }
    }

}