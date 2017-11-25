package ru.nobird.junction.api.sensor

import android.util.Log
import com.google.gson.Gson
import com.movesense.mds.*
import ru.nobird.junction.util.FormatHelper
import ru.nobird.junction.model.InfoResponse
import ru.nobird.junction.model.LinearAcceleration

class AngularVelocitySensor(private val mds: Mds, private val serial: String) {
    private var avSubscription: MdsSubscription? = null
    var rates: IntArray? = null
        private set

    init {
        mds.get(FormatHelper.SCHEME_PREFIX + serial + INFO_PATH, null, object : MdsResponseListener {
            override fun onSuccess(data: String) {
                Log.d(TAG, "onSuccess(): " + data)

                val infoResponse = Gson().fromJson(data, InfoResponse::class.java)
                rates = infoResponse.content.sampleRates
            }

            override fun onError(error: MdsException) {
                Log.e(TAG, "onError(): ", error)
            }
        })
    }

    fun subscribe(rate: Int, listener: Any) {
        avSubscription = mds.subscribe(FormatHelper.URI_EVENT_LISTENER,
                FormatHelper.formatContractToJson(serial, PATH + rate),
                object : MdsNotificationListener {
                    override fun onNotification(data: String) {
                        Log.d(TAG, "onSuccess(): " + data)

                        val linearAccelerationData = Gson().fromJson(data, LinearAcceleration::class.java)

                        val arrayData = linearAccelerationData?.body?.array?.get(0)

                        // vector here

                    }

                    override fun onError(error: MdsException) {
                        Log.e(TAG, "onError(): ", error)
                    }
                })
    }

    fun unsubscribe() {
        avSubscription?.unsubscribe()
    }

    companion object {
        private val TAG = "AngularVelocityS"

        private val PATH = "Meas/Gyro/"
        private val INFO_PATH = "/Meas/Gyro/Info"
    }
}