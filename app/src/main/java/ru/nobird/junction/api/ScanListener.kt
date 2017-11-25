package ru.nobird.junction.api

interface ScanListener {
    fun found(device: MoveSenseDevice)
}