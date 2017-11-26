package ru.nobird.junction

import android.app.Application
import android.content.Context
import ru.nobird.junction.sound.MetronomeSounds

class App: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        MetronomeSounds.init(this.applicationContext)
    }
}