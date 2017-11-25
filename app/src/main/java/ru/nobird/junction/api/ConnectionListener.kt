package ru.nobird.junction.api

import com.movesense.mds.MdsException

/**
 * Created by lytr777 on 25/11/2017.
 */
interface ConnectionListener {
    fun connected(device: MoveSenseDevice)
    fun failed(error: MdsException?)
    fun disconnected()
}