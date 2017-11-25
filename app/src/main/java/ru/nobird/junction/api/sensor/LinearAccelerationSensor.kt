package ru.nobird.junction.api.sensor

import android.util.Log
import com.google.gson.Gson
import com.movesense.mds.*
import ru.nobird.junction.model.InfoResponse
import ru.nobird.junction.model.LinearAcceleration
import ru.nobird.junction.util.FormatHelper

class LinearAccelerationSensor(private val mds: Mds, private val serial: String) {
    private var laSubscription: MdsSubscription? = null
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
        laSubscription = mds.subscribe(FormatHelper.URI_EVENT_LISTENER,
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
        laSubscription?.unsubscribe()
    }

    companion object {
        private val TAG = "LinearAccelerationS"

        private val PATH = "Meas/Acc/"
        private val INFO_PATH = "/Meas/Acc/Info"
    }
}