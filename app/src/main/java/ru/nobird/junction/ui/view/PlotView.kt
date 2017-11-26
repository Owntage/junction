package ru.nobird.junction.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ru.nobird.junction.data.PlotBuffer
import ru.nobird.junction.model.TimeData

class PlotView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var baselineY = 0f
    private var mult = 1f

    val data = PlotBuffer(500, TimeData(0, 0f))

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF58D088.toInt()
    }

    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0x7758D088
    }

    override fun onDraw(canvas: Canvas) {
        val dx = width.toFloat() / data.size
        mult = height.toFloat() / 2
        for (i in 1 until data.size) {
            canvas.drawLine(dx * (i - 1), data[i - 1].value * mult, dx * i, data[i].value * mult, paint)
            canvas.drawLine(dx * (i - 1), data[i - 1].value * mult, dx * (i - 1), height.toFloat(), paint2)
        }
    }

}