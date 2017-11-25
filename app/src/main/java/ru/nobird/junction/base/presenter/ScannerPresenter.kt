package ru.nobird.junction.base.presenter

import ru.nobird.junction.App
import ru.nobird.junction.api.ScanClient
import ru.nobird.junction.base.presenter.contract.ScannerView
import ru.nobird.junction.ui.DeviceAdapter

class ScannerPresenter: PresenterBase<ScannerView>() {
    companion object : PresenterFactory<ScannerPresenter> {
        override fun create() = ScannerPresenter()
    }

    private val scanClient: ScanClient = ScanClient(App.context)
    private val adapter = DeviceAdapter()
    init {
        scanClient.connect {
            adapter.add(it)
        }
    }

    override fun attachView(view: ScannerView) {
        super.attachView(view)
        view.setDeviceAdapter(adapter)
    }

    override fun detachView(view: ScannerView) {
        super.detachView(view)
    }

    override fun destroy() {
        scanClient.disconnect()
    }
}