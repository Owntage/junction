package ru.nobird.junction.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.nobird.junction.R
import ru.nobird.junction.api.MoveSenseDevice
import ru.nobird.junction.base.presenter.BasePresenterActivity
import ru.nobird.junction.base.presenter.ConnectionPresenter
import ru.nobird.junction.base.presenter.contract.ConnectionView


class ConnectionActivity: BasePresenterActivity<ConnectionPresenter, ConnectionView>(), ConnectionView {
    companion object {
        private const val DEVICE_KEY = "device_key"

        fun show(parent: Context, device: MoveSenseDevice) {
            val intent = Intent(parent, ConnectionActivity::class.java)
            intent.putExtra(DEVICE_KEY, device)
            parent.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)
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

    override fun getPresenterFactory() = ConnectionPresenter.Companion
}