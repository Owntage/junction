package ru.nobird.junction.base.presenter

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import ru.nobird.junction.base.loader.PresenterLoader

abstract class BasePresenterActivity<P : Presenter<V>, in V> : AppCompatActivity() {
    private companion object {
        private const val LOADER_ID = 127
    }
    private var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loader = supportLoaderManager.getLoader<P>(LOADER_ID)
        if (loader == null) {
            initLoader()
        } else {
            presenter = (loader as PresenterLoader<P>).presenter
            onPresenter(presenter!!)
        }
    }

    private fun initLoader() {
        supportLoaderManager.initLoader(LOADER_ID, null, object : LoaderManager.LoaderCallbacks<P> {
            override fun onLoadFinished(loader: Loader<P>?, data: P) {
                presenter = data
                onPresenter(data)
            }

            override fun onLoaderReset(loader: Loader<P>?) {
                presenter = null
            }

            override fun onCreateLoader(id: Int, args: Bundle?): Loader<P> =
                    PresenterLoader(this@BasePresenterActivity, getPresenterFactory())
        })
    }

    protected abstract fun onPresenter(presenter: P)
    protected abstract fun getPresenterFactory() : PresenterFactory<P>
}