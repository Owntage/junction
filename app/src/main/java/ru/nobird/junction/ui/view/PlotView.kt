package ru.nobird.junction.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ru.nobird.junction.data.PlotBuffer
import ru.nobird.junction.model.PlotData
import ru.nobird.junction.model.TimeData

class PlotView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var baselineY = 0f
    private var mult = 1f

    val data = PlotBuffer(500, PlotData(0.0, 0.0, 0.0))

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF58D088.toInt()
    }

    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0x7758D088
    }

    private val paint3 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF15C7CA.toInt()
    }

    private val paint4 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0x7715C7CA
    }

    override fun onDraw(canvas: Canvas) {
        val dx = width.toFloat() / data.size
        mult = height.toFloat() / 2
        for (i in 1 until data.size) {
            canvas.drawLine(dx * (i - 1), (1.0 - data[i - 1].idealMagnitude).toFloat() * mult,
                    dx * i, (1.0 - data[i].idealMagnitude).toFloat() * mult, paint)
            canvas.drawLine(dx * (i - 1), (1.0 - data[i - 1].idealMagnitude).toFloat() * mult,
                    dx * (i - 1), height.toFloat() / 2, paint2)

            canvas.drawLine(dx * (i - 1), mult + data[i - 1].actualMagnitude.toFloat() * mult,
                    dx * i, mult + data[i].actualMagnitude.toFloat() * mult, paint3)
            canvas.drawLine(dx * (i - 1), mult + data[i - 1].actualMagnitude.toFloat() * mult,
                    dx * (i - 1), height.toFloat() / 2, paint4)
        }
    }

}