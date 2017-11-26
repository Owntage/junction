package ru.nobird.junction.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_device.view.*
import ru.nobird.junction.R
import ru.nobird.junction.api.MoveSenseDevice

class DeviceAdapter(private val onClick: (MoveSenseDevice) -> Unit): RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>(){
    private val devices = ArrayList<MoveSenseDevice>()

    override fun getItemCount() = devices.size

    fun add(device: MoveSenseDevice) {
        if (!devices.contains(device)) {
            devices.add(device)
            notifyItemInserted(devices.size - 1)
        }
    }

    fun setData(devices: List<MoveSenseDevice>) {
        devices.forEach { add(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = DeviceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false))

    override fun onBindViewHolder(holder: DeviceViewHolder, p: Int) {
        holder.mac.text = devices[p].macAddress
        holder.name.text = devices[p].name

        holder.itemView.setOnClickListener { onClick(devices[p]) }
    }

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mac = view.mac
        val name = view.name
    }
}