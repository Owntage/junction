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
    private val mBleClient = RxBleClient.create(context)

    fun connect(device: MoveSenseDevice): Single<String> = Single.create { s ->
        val bleDevice = mBleClient.getBleDevice(device.macAddress)
        mds.connect(bleDevice.macAddress, object : MdsConnectionListener {
            override fun onConnect(macAddress: String) = Unit

            override fun onConnectionComplete(macAddress: String, serial: String) {
                Log.d(TAG, "onConnectionComplete: $macAddress $serial")

                device.markConnected(serial)
                s.onSuccess(serial)
            }

            override fun onError(error: MdsException) = s.onError(error)

            override fun onDisconnect(bleAddress: String) {
                device.markDisconnected()
                s.onError(IllegalStateException("Disconnected $bleAddress"))
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