package ru.nobird.junction.data

class PlotBuffer(size: Int) {
    private val buff = FloatArray(size * 3) { 0f }
    private var start = 0
    private var end = size

    val size: Int
        get() = if (start >= end) {
            buff.size - start + end
        } else {
            end - start
        }

    fun add(y: Float) {
        if (end < buff.size) {
            end++
        } else {
            end = 1
        }

        if (start < buff.size - 1) {
            start++
        } else {
            start = 0
        }

        buff[end - 1] = y
    }

    operator fun get(i: Int) = buff[(start + i) % buff.size]

    override fun toString() = buff.joinToString(", ")
}