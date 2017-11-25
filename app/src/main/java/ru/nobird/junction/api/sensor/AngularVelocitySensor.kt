package ru.nobird.junction.api.sensor

import android.content.Context
import android.util.Log
import com.movesense.mds.Mds
import com.movesense.mds.MdsException
import com.movesense.mds.MdsResponseListener
import com.movesense.mds.internal.connectivity.MovesenseConnectedDevices

/**
 * Created by lytr777 on 25/11/2017.
 */
class AngularVelocitySensor {

    fun getRates(context: Context, serial: String) {
        Mds.builder().build(context).get(SCHEME_PREFIX + serial + INFO_PATH, null, object : MdsResponseListener {
            override fun onSuccess(data: String) {
                Log.d(TAG, "onSuccess(): " + data)

//                val infoResponse = Gson().fromJson(data, InfoResponse::class.java)
//
//                for (inforate in infoResponse.content.sampleRates) {
//                    rates.add(inforate.toString())
//                }
            }

            override fun onError(error: MdsException) {
                Log.e(TAG, "onError(): ", error)

            }
        })
    }

    companion object {
        private val TAG = "AngularVelocitySensor"

        val SCHEME_PREFIX = "suunto://"

        private val PATH = "Meas/Gyro/"
        private val INFO_PATH = "/Meas/Gyro/Info"
    }
}