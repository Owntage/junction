package ru.nobird.junction.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_plot.*
import ru.nobird.junction.R
import ru.nobird.junction.model.TimeData
import java.sql.Time

class PlotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plot)

        val behavior = PublishSubject.create<TimeData>()
        behavior
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    plot.data.add(it)
                    plot.invalidate()
                }

        Thread(Runnable {
            var i = 0.0
            while (true) {
                i += 1
                behavior.onNext(TimeData(System.nanoTime(), 1f - (Math.cos(i / 25).toFloat())))
                Thread.sleep(10L)
            }
        }).start()
    }

}