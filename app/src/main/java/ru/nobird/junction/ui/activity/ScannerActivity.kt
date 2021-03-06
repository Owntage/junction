package ru.nobird.junction.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_scanner.*
import ru.nobird.junction.R
import ru.nobird.junction.base.presenter.BasePresenterActivity
import ru.nobird.junction.base.presenter.ScannerPresenter
import ru.nobird.junction.base.presenter.contract.ScannerView
import ru.nobird.junction.ui.DeviceAdapter

class ScannerActivity : BasePresenterActivity<ScannerPresenter, ScannerView>(), ScannerView {
    private companion object {
        private const val PROGRESS_DIALOG_TAG = "progress_dialog"
    }

    override fun onLoading() {
        showProgressDialogFragment(PROGRESS_DIALOG_TAG, getString(R.string.connection_progress_title), getString(R.string.connection_progress_msg))
    }

    override fun onSuccess(serial: String) {
        hideProgressDialogFragment(PROGRESS_DIALOG_TAG)
        Snackbar.make(findViewById(android.R.id.content), "Success", Snackbar.LENGTH_LONG).show()
        PickerActivity.show(this, serial)
    }

    override fun onError() {
        hideProgressDialogFragment(PROGRESS_DIALOG_TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scanner)
        deviceList.layoutManager = LinearLayoutManager(this)
        requestNeededPermissions()
    }

    override fun setDeviceAdapter(adapter: DeviceAdapter) {
        deviceList.adapter = adapter
    }

    private fun requestNeededPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)

        }
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