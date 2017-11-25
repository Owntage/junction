package ru.nobird.junction

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.nobird.junction.ui.activity.PlotActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this, PlotActivity::class.java))
    }
}
