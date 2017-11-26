package ru.nobird.junction.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_picker.*
import ru.nobird.junction.R


class PickerActivity: AppCompatActivity() {
    companion object {
        private const val SERIAL_KEY = "serial_key"

        fun show(parent: Context, serial: String) {
            val intent = Intent(parent, PickerActivity::class.java)
            intent.putExtra(SERIAL_KEY, serial)
            parent.startActivity(intent)
        }
    }

    private var tempoVar = 60
    private val tempoD = 20
    private val tempoMax = 240
    private val tempoMin = 20


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picker)

        addTempo.setOnClickListener {
            tempoVar = Math.min(tempoMax, tempoD + tempoVar)
            tempo.text = tempoVar.toString()
        }

        reduceTempo.setOnClickListener {
            tempoVar = Math.max(tempoMin, tempoVar - tempoD)
            tempo.text = tempoVar.toString()
        }

        startButton.setOnClickListener {
            ConnectionActivity.show(this, intent.getStringExtra(SERIAL_KEY))
        }
    }
}