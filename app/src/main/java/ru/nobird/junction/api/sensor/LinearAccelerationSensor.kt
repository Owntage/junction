package ru.nobird.junction.api.sensor

import android.util.Log
import com.google.gson.Gson
import com.movesense.mds.Mds
import com.movesense.mds.MdsException
import com.movesense.mds.MdsNotificationListener
import com.movesense.mds.MdsSubscription
import ru.nobird.junction.model.LinearAcceleration
import ru.nobird.junction.util.FormatHelper

/**
 * Created by lytr777 on 25/11/2017.
 */
class LinearAccelerationSensor {


    fun getSubscribe(mds: Mds, serial: String, rate: Int, listener: Any): MdsSubscription {
        return mds.subscribe(FormatHelper.URI_EVENT_LISTENER,
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

    companion object {
        private val TAG = "LinearAccelerationS"

        private val PATH = "Meas/Acc/"
        private val INFO_PATH = "/Meas/Acc/Info"
    }
}