package ru.nobird.junction.api

import android.content.Context
import android.util.Log
import com.movesense.mds.Mds
import com.movesense.mds.MdsConnectionListener
import com.movesense.mds.MdsException
import com.polidea.rxandroidble.RxBleClient
import io.reactivex.Single

class ConnectManager(context: Context) {
    private val mds = Mds.builder().build(context)
    private val bleClient = RxBleClient.create(context)

    fun connect(device: MoveSenseDevice): Single<String> = Single.create { s ->
        if (device.isConnected()) throw IllegalStateException("Device already connected")

        val bleDevice = bleClient.getBleDevice(device.macAddress)
        mds.connect(bleDevice.macAddress, object : MdsConnectionListener {
            override fun onConnect(macAddress: String) = Unit

            override fun onConnectionComplete(macAddress: String, serial: String) {
                Log.d(TAG, "onConnectionComplete: $macAddress $serial")

                device.markConnected(serial)
                s.onSuccess(serial)
            }

            override fun onError(error: MdsException) = s.onError(error)

            override fun onDisconnect(bleAddress: String) {
                Log.e(TAG, "Disconnected: $bleAddress")
                device.markDisconnected()
            }
        })
    }

    fun disconnect(device: MoveSenseDevice) {
        if (device.isConnected())
            mds.disconnect(device.macAddress)
    }

    companion object {
        private const val TAG = "ConnectManager"
    }
}