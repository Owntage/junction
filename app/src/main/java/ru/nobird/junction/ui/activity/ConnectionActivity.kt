package ru.nobird.junction.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_plot.*
import ru.nobird.junction.R
import ru.nobird.junction.api.MoveSenseDevice
import ru.nobird.junction.base.presenter.BasePresenterActivity
import ru.nobird.junction.base.presenter.ConnectionPresenter
import ru.nobird.junction.base.presenter.PresenterFactory
import ru.nobird.junction.base.presenter.contract.ConnectionView
import ru.nobird.junction.model.AngularVelocity
import ru.nobird.junction.model.PlotData
import ru.nobird.junction.model.TimeData


class ConnectionActivity: BasePresenterActivity<ConnectionPresenter, ConnectionView>(), ConnectionView {
    companion object {
        private const val DEVICE_KEY = "device_key"
        private const val SERIAL_KEY = "serial_key"

        fun show(parent: Context, device: MoveSenseDevice) {
            val intent = Intent(parent, ConnectionActivity::class.java)
            intent.putExtra(DEVICE_KEY, device)
            parent.startActivity(intent)
        }

        fun show(parent: Context, serial: String) {
            val intent = Intent(parent, ConnectionActivity::class.java)
            intent.putExtra(SERIAL_KEY, serial)
            parent.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plot)
    }

    override fun onData(pd: PlotData) {
        plot.data.add(pd)
        plot.invalidate()
    }

    override fun onSuccess() {

    }

    override fun onError() {

    }

    override fun onStart() {
        super.onStart()
        presenter?.attachView(this)
    }

    override fun onStop() {
        presenter?.detachView(this)
        super.onStop()
    }

    override fun getPresenterFactory() = object : PresenterFactory<ConnectionPresenter> {
        override fun create(): ConnectionPresenter {
            val serial = this@ConnectionActivity.intent.getStringExtra(SERIAL_KEY)
            return ConnectionPresenter(serial)
        }
    }
}