package ru.nobird.junction.base.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.nobird.junction.App
import ru.nobird.junction.api.ConnectManager
import ru.nobird.junction.api.MoveSenseDevice
import ru.nobird.junction.api.ScanClient
import ru.nobird.junction.base.presenter.contract.ScannerView
import ru.nobird.junction.ui.DeviceAdapter

class ScannerPresenter: PresenterBase<ScannerView>() {
    companion object : PresenterFactory<ScannerPresenter> {
        override fun create() = ScannerPresenter()
    }

    private val scanClient = ScanClient(App.context)
    private val connectManager = ConnectManager(App.context)
    private val adapter = DeviceAdapter { connectToDevice(it) }

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

    private fun connectToDevice(device: MoveSenseDevice) {
        view?.onLoading()
        connectManager.connect(device).observeOn(AndroidSchedulers.mainThread()).subscribe({
            view?.onSuccess()
        }, {
            view?.onError()
        })
    }
}