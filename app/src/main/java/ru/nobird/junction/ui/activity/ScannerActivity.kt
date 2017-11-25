package ru.nobird.junction.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_scanner.*
import ru.nobird.junction.R
import ru.nobird.junction.base.presenter.BasePresenterActivity
import ru.nobird.junction.base.presenter.ScannerPresenter
import ru.nobird.junction.base.presenter.contract.ScannerView
import ru.nobird.junction.ui.DeviceAdapter

class ScannerActivity : BasePresenterActivity<ScannerPresenter, ScannerView>(), ScannerView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scanner)
        deviceList.layoutManager = LinearLayoutManager(this)
    }

    override fun setDeviceAdapter(adapter: DeviceAdapter) {
        deviceList.adapter = adapter
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