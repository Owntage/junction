package ru.nobird.junction.api

import com.movesense.mds.MdsException

interface ConnectionListener {
    fun connected(device: MoveSenseDevice)
    fun failed(error: MdsException?)
    fun disconnected()
}