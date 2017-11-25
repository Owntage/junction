package ru.nobird.junction.base.presenter

import com.movesense.mds.Mds
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.nobird.junction.App
import ru.nobird.junction.api.sensor.AngularVelocitySensor
import ru.nobird.junction.base.presenter.contract.ConnectionView


class ConnectionPresenter(serial: String): PresenterBase<ConnectionView>() {
    private val avSensor = AngularVelocitySensor(Mds.builder().build(App.context), serial)

    init {
        avSensor.subscribe(208).observeOn(AndroidSchedulers.mainThread()).subscribe {
            view?.onData(it)
        }
    }

    override fun destroy() {
        avSensor.unsubscribe()
    }
}