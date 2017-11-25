package ru.nobird.junction.base.presenter.contract

import ru.nobird.junction.ui.DeviceAdapter

interface ScannerView {
    fun setDeviceAdapter(adapter: DeviceAdapter)
    fun onLoading()
    fun onSuccess(serial: String)
    fun onError()
}