package ru.nobird.junction.base.presenter

import com.movesense.mds.Mds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import ru.nobird.junction.App
import ru.nobird.junction.api.sensor.AngularVelocitySensor
import ru.nobird.junction.api.sensor.LinearAccelerationSensor
import ru.nobird.junction.base.presenter.contract.ConnectionView
import ru.nobird.junction.model.AngularVelocity
import ru.nobird.junction.model.PlotData
import ru.nobird.junction.model.Vec3f
import ru.nobird.junction.processing.PlotManager


class ConnectionPresenter(serial: String): PresenterBase<ConnectionView>() {
    private val avSensor = AngularVelocitySensor(Mds.builder().build(App.context), serial)
    private val laSensor = LinearAccelerationSensor(Mds.builder().build(App.context), serial)

    private val publisher = PublishSubject.create<PlotData>()
    private val plotManager = PlotManager(publisher)

    private val timerThread = Thread(Runnable {
        try {
            while (!Thread.currentThread().isInterrupted) {
                plotManager.update(System.currentTimeMillis())
                Thread.sleep(10L)
            }
        } catch (e: Exception) {}
    })

    init {
        avSensor.subscribe(13).observeOn(AndroidSchedulers.mainThread()).subscribe ({
            //view?.onData(it)
            //plotManager.pingListener.onPingChanged(0)
            //plotManager.realListener.onLinearAcceleration()
        }, {})

        laSensor.subscribe(13).observeOn(AndroidSchedulers.mainThread()).subscribe ({
            //val a = AngularVelocity.Array(it.x / 10, it.y / 10, it.z / 10)
            //view?.onData(a)
            plotManager.pingListener.onPingChanged(150)
            plotManager.realListener.onLinearAcceleration(
                    Vec3f(it.x.toFloat(),
                    it.y.toFloat(), it.z.toFloat()), System.currentTimeMillis());
        }, {})

        publisher.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view?.onData(it)
        }

        timerThread.start()
    }

    override fun destroy() {
        timerThread.interrupt()
        avSensor.unsubscribe()
    }
}