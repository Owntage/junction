package ru.nobird.junction.base.presenter.contract

import ru.nobird.junction.ui.DeviceAdapter

interface ScannerView {
    fun setDeviceAdapter(adapter: DeviceAdapter)
}