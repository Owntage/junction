package ru.nobird.junction.api

import com.polidea.rxandroidble.RxBleDevice
import com.polidea.rxandroidble.scan.ScanResult

class MoveSenseDevice(scanResult: ScanResult) {
    var rssi = scanResult.rssi
    var macAddress = scanResult.bleDevice.macAddress
    var name = scanResult.bleDevice.name
    var connectedSerial: String? = null

    fun isConnected(): Boolean {
        return connectedSerial != null
    }

    fun markConnected(serial: String) {
        connectedSerial = serial
    }

    fun markDisconnected() {
        connectedSerial = null
    }

    override fun equals(other: Any?): Boolean {
        if (other is MoveSenseDevice)
            if (other.macAddress == this.macAddress)
                return true

        if (other is RxBleDevice)
            if (other.macAddress == this.macAddress)
                return true

        return false
    }

    override fun hashCode() = macAddress.hashCode()
}