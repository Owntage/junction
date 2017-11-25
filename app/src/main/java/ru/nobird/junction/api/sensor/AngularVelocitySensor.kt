package ru.nobird.junction.api.sensor

import android.util.Log
import com.google.gson.Gson
import com.movesense.mds.*
import ru.nobird.junction.api.FormatHelper
import ru.nobird.junction.model.InfoResponse
import ru.nobird.junction.model.LinearAcceleration

/**
 * Created by lytr777 on 25/11/2017.
 */
class AngularVelocitySensor {

    fun getRates(mds: Mds, serial: String) {
        mds.get(FormatHelper.SCHEME_PREFIX + serial + INFO_PATH, null, object : MdsResponseListener {
            override fun onSuccess(data: String) {
                Log.d(TAG, "onSuccess(): " + data)

                val infoResponse = Gson().fromJson(data, InfoResponse::class.java)

//                for (inforate in infoResponse.content.sampleRates) {
//                    rates.add(inforate.toString())
//                }
            }

            override fun onError(error: MdsException) {
                Log.e(TAG, "onError(): ", error)

            }
        })
    }

    fun subscribe(mds: Mds, serial: String, rate: Int, listener: Any): MdsSubscription {
        return mds.subscribe(URI_EVENT_LISTENER, FormatHelper.formatContractToJson(serial, PATH + rate), object : MdsNotificationListener {
            override fun onNotification(data: String) {
                Log.d(TAG, "onSuccess(): " + data)

                val linearAccelerationData = Gson().fromJson(data, LinearAcceleration::class.java)

                    val arrayData = linearAccelerationData?.body?.array?.get(0)

//                    xAxisLinearAccTextView.setText(String.format(Locale.getDefault(),
//                            "x: %.6f", arrayData.x))
//                    yAxisLinearAccTextView.setText(String.format(Locale.getDefault(),
//                            "y: %.6f", arrayData.y))
//                    zAxisLinearAccTextView.setText(String.format(Locale.getDefault(),
//                            "z: %.6f", arrayData.z))

            }

            override fun onError(error: MdsException) {
                Log.e(TAG, "onError(): ", error)
            }
        })
    }

    companion object {
        private val TAG = "AngularVelocitySensor"

        val URI_EVENT_LISTENER = "suunto://MDS/EventListener"

        private val PATH = "Meas/Gyro/"
        private val INFO_PATH = "/Meas/Gyro/Info"
    }
}