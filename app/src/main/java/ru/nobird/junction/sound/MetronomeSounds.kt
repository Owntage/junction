package ru.nobird.junction.sound

import android.content.Context
import android.media.MediaPlayer
import ru.nobird.junction.R

object MetronomeSounds {

    private lateinit var strong: MediaPlayer
    private lateinit var weak: MediaPlayer

    fun init(context: Context) {
        strong = MediaPlayer.create(context, R.raw.tick)
        weak = MediaPlayer.create(context, R.raw.pat)
    }

    fun playStrong() {
        strong.start()
    }

    fun playWeak() {
        weak.start()
    }
}