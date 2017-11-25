package ru.nobird.junction.api

import android.content.Context
import android.util.Log
import com.polidea.rxandroidble.RxBleClient
import com.polidea.rxandroidble.scan.ScanSettings
import rx.Subscription

class ScanClient(context: Context) {
    private val mBleClient = RxBleClient.create(context)
    private var mScanSubscription: Subscription? = null

    fun connect(listener: (MoveSenseDevice) -> Unit) {
        mScanSubscription = mBleClient.scanBleDevices(
                ScanSettings.Builder()
                        // .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY) // change if needed
                        // .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES) // change if needed
                        .build()
                // add filters if needed
        ).subscribe({ scanResult ->
//                    Log.d(TAG, "Scan result: " + scanResult)

                    if (scanResult.bleDevice?.name?.startsWith(PREFIX) == true) {
                        val msd = MoveSenseDevice(scanResult.rssi, scanResult.bleDevice.macAddress, scanResult.bleDevice.name)
                        listener(msd)
                    }
                }, { throwable ->
//                    Log.e(TAG, "Scan error: " + throwable)
                    disconnect()
                }
        )
    }

    fun disconnect() {
        mScanSubscription?.unsubscribe()
        mScanSubscription = null
    }

    companion object {
        private const val TAG = "ScanClient"
        private const val PREFIX = "Movesense"
    }
}