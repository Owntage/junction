package ru.nobird.junction

import org.junit.Test
import ru.nobird.junction.data.PlotBuffer


class BuffTest {

    @Test
    fun defaultTest() {
        val b = PlotBuffer(10)
        for (i in 0..250) {
            b.add(i.toFloat() / 250)
        }

        println(b)

    }

}