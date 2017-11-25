package ru.nobird.junction.ui.activity

import ru.nobird.junction.base.presenter.BasePresenterActivity
import ru.nobird.junction.base.presenter.ScannerPresenter
import ru.nobird.junction.base.presenter.contract.ScannerView

class ScannerActivity : BasePresenterActivity<ScannerPresenter, ScannerView>(), ScannerView {
    override fun setDeviceList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        super.onStart()
        presenter?.attachView(this)
    }

    override fun onStop() {
        presenter?.detachView(this)
        super.onStop()
    }

    override fun getPresenterFactory() = ScannerPresenter.Companion
}