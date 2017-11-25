package ru.nobird.junction.api

import android.content.Context
import android.util.Log
import com.movesense.mds.Mds
import com.movesense.mds.MdsConnectionListener
import com.movesense.mds.MdsException
import com.polidea.rxandroidble.RxBleClient

class ConnectManager(context: Context) {
    private val mds = Mds.builder().build(context)
    private val mBleClient = RxBleClient.create(context)

    private var connectedDevice: MoveSenseDevice? = null

    fun connect(device: MoveSenseDevice, listener: ConnectionListener) {
        disconnect()

        val bleDevice = mBleClient.getBleDevice(device.macAddress)
        mds.connect(bleDevice.macAddress, object : MdsConnectionListener {

            override fun onConnect(s: String) {
                Log.d(TAG, "onConnect: " + s)

                connectedDevice = device
                listener.connecting(device)
            }

            override fun onConnectionComplete(macAddress: String, serial: String) {
                Log.d(TAG, "onConnectionComplete: " + serial)

                device.markConnected(serial)
                listener.connected(device)
            }

            override fun onError(error: MdsException) {
                Log.d(TAG, "onError: " + error)

                listener.failed(error)
            }

            override fun onDisconnect(bleAddress: String) {
                Log.d(TAG, "onDisconnect: " + bleAddress)

                device.markDisconnected()
                listener.disconnected(device)
            }
        })
    }

    fun disconnect() {
        connectedDevice?.let {
            mds.disconnect(it.macAddress)
            connectedDevice = null
        }
    }

    companion object {
        private const val TAG = "ConnectManager"
    }
}