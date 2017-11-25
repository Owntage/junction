package ru.nobird.junction.base.presenter

import ru.nobird.junction.base.presenter.contract.ScannerView

class ScannerPresenter: PresenterBase<ScannerView>() {
    companion object : PresenterFactory<ScannerPresenter> {
        override fun create() = ScannerPresenter()
    }

    override fun attachView(view: ScannerView) {
        super.attachView(view)
//        view.setDeviceList()
    }

    override fun detachView(view: ScannerView) {
        super.detachView(view)
    }

    override fun destroy() {

    }
}