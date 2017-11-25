package ru.nobird.junction.api

import android.os.Parcel
import android.os.Parcelable
import com.polidea.rxandroidble.RxBleDevice

class MoveSenseDevice(val rssi: Int,
                      val macAddress: String,
                      val name: String?) : Parcelable {
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
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(rssi)
        parcel.writeString(macAddress)
        parcel.writeString(name)
        parcel.writeString(connectedSerial)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<MoveSenseDevice> {
        override fun createFromParcel(parcel: Parcel)
                = MoveSenseDevice(parcel.readInt(), parcel.readString(), parcel.readString()).apply {
            connectedSerial = parcel.readString()
        }

        override fun newArray(size: Int) = arrayOfNulls<MoveSenseDevice>(size)
    }
}