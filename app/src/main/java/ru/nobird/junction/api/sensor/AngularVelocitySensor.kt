package ru.nobird.junction.api.sensor

import android.util.Log
import com.google.gson.Gson
import com.movesense.mds.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.nobird.junction.model.AngularVelocity
import ru.nobird.junction.util.FormatHelper
import ru.nobird.junction.model.InfoResponse

class AngularVelocitySensor(private val mds: Mds, private val serial: String) {
    private var avSubscription: MdsSubscription? = null
    var rates: IntArray? = null
        private set

    init {
        mds.get(FormatHelper.SCHEME_PREFIX + serial + INFO_PATH, null, object : MdsResponseListener {
            override fun onSuccess(data: String) {
//                Log.d(TAG, "onSuccess(): " + data)

                val infoResponse = Gson().fromJson(data, InfoResponse::class.java)
                rates = infoResponse.content.sampleRates
            }

            override fun onError(error: MdsException) {
                Log.e(TAG, "onError(): ", error)
            }
        })
    }

    fun subscribe(rate: Int): Observable<AngularVelocity.Array> = Observable.create<AngularVelocity.Array> { c ->
        avSubscription = mds.subscribe(FormatHelper.URI_EVENT_LISTENER,
                FormatHelper.formatContractToJson(serial, PATH + rate),
                object : MdsNotificationListener {
                    override fun onNotification(data: String) {
//                        Log.d(TAG, "onSuccess(): " + data)
                        val linearAccelerationData = Gson().fromJson(data, AngularVelocity::class.java)
                        val arrayData = linearAccelerationData.body.array[0]
                        c.onNext(arrayData)
                    }

                    override fun onError(error: MdsException) {
                        Log.e(TAG, "onError(): ", error)
                        c.onError(error)
                    }
                })
    }.subscribeOn(Schedulers.io())

    fun unsubscribe() {
        avSubscription?.unsubscribe()
    }

    companion object {
        private const val TAG = "AngularVelocityS"

        private const val PATH = "Meas/Gyro/"
        private const val INFO_PATH = "/Meas/Gyro/Info"
    }
}