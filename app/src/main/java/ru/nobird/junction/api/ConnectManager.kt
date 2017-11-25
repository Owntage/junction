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

    fun connect(device: MoveSenseDevice): Connection? {
        if (device.isConnected())
            return null

        val connection = Connection(device, mds)
        val bleDevice = mBleClient.getBleDevice(device.macAddress)
        mds.connect(bleDevice.macAddress, object : MdsConnectionListener {

            override fun onConnect(s: String) {
                Log.d(TAG, "onConnect: " + s)
            }

            override fun onConnectionComplete(macAddress: String, serial: String) {
                Log.d(TAG, "onConnectionComplete: " + serial)

                device.markConnected(serial)
                connection.connected()
            }

            override fun onError(error: MdsException) {
                Log.d(TAG, "onError: " + error)

                connection.failed(error)
            }

            override fun onDisconnect(bleAddress: String) {
                Log.d(TAG, "onDisconnect: " + bleAddress)

                device.markDisconnected()
                connection.disconnected()
            }
        })
        return connection
    }

    fun disconnect(connection: Connection) {
        if (connection.device.isConnected())
            mds.disconnect(connection.device.macAddress)
    }

    companion object {
        private const val TAG = "ConnectManager"
    }
}