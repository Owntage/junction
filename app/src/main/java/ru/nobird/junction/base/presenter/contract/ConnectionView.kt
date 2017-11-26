package ru.nobird.junction.base.presenter.contract

import ru.nobird.junction.model.AngularVelocity

interface ConnectionView {
    fun onData(v3: AngularVelocity.Array)
    fun onSuccess()
    fun onError()
}