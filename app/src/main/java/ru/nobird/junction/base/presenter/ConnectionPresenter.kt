package ru.nobird.junction.base.presenter

import com.movesense.mds.Mds
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.nobird.junction.App
import ru.nobird.junction.api.sensor.AngularVelocitySensor
import ru.nobird.junction.api.sensor.LinearAccelerationSensor
import ru.nobird.junction.base.presenter.contract.ConnectionView
import ru.nobird.junction.model.AngularVelocity


class ConnectionPresenter(serial: String): PresenterBase<ConnectionView>() {
    private val avSensor = AngularVelocitySensor(Mds.builder().build(App.context), serial)
    private val laSensor = LinearAccelerationSensor(Mds.builder().build(App.context), serial)

    init {
        avSensor.subscribe(13).observeOn(AndroidSchedulers.mainThread()).subscribe ({
            view?.onData(it)
        }, {})

        laSensor.subscribe(13).observeOn(AndroidSchedulers.mainThread()).subscribe ({
            val a = AngularVelocity.Array(it.x / 10, it.y / 10, it.z / 10)
            view?.onData(a)
        }, {})
    }

    override fun destroy() {
        avSensor.unsubscribe()
    }
}