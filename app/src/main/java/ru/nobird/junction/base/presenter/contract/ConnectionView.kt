package ru.nobird.junction.base.presenter.contract

import ru.nobird.junction.model.PlotData

interface ConnectionView {
    fun onData(pd: PlotData)
    fun onSuccess()
    fun onError()
}