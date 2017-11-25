package ru.nobird.junction.api.sensor

import com.movesense.mds.Mds
import com.movesense.mds.MdsException
import com.movesense.mds.MdsResponseListener
import com.movesense.mds.MdsSubscription
import io.reactivex.Observable
import ru.nobird.junction.util.FormatHelper
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * Created by lytr777 on 25/11/2017.
 */
class PingSensor(private val mds: Mds, private val serial: String) {
    private var bSubscription: MdsSubscription? = null

    fun subscribe(): Observable<Long> = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .flatMap {
                val start = System.nanoTime()
                Observable.create<Long> { c ->
                    mds.get(FormatHelper.SCHEME_PREFIX + serial + PATH,null, object : MdsResponseListener {
                        override fun onSuccess(s: String) {
                            c.onNext(System.nanoTime() - start)
                        }

                        override fun onError(e: MdsException) {
                            c.onError(e)
                        }
                    })
                }
            }

    fun unsubscribe() {

    }

    companion object {
        private const val TAG = "PingSensor"

        private const val PATH = "/System/Energy/Level"
    }
}