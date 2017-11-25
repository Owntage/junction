package ru.nobird.junction.base.presenter

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.DialogFragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import ru.nobird.junction.base.loader.PresenterLoader
import ru.nobird.junction.ui.dialog.ProgressDialogFragment

abstract class BasePresenterActivity<P : Presenter<V>, in V> : AppCompatActivity() {
    private companion object {
        private const val LOADER_ID = 127
    }
    protected var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loader = supportLoaderManager.getLoader<P>(LOADER_ID)
        if (loader == null) {
            initLoader()
        } else {
            onPresenter((loader as PresenterLoader<P>).presenter)
        }
    }

    private fun initLoader() {
        supportLoaderManager.initLoader(LOADER_ID, null, object : LoaderManager.LoaderCallbacks<P> {
            override fun onLoadFinished(loader: Loader<P>?, data: P) {
                onPresenter(data)
            }

            override fun onLoaderReset(loader: Loader<P>?) {
                presenter = null
            }

            override fun onCreateLoader(id: Int, args: Bundle?): Loader<P> =
                    PresenterLoader(this@BasePresenterActivity, getPresenterFactory())
        })
    }

    protected fun showProgressDialogFragment(tag: String, title: String, msg: String) {
        ProgressDialogFragment.newInstance(title, msg).show(supportFragmentManager, tag)
    }
    protected fun hideProgressDialogFragment(tag: String) {
        val dialog = supportFragmentManager.findFragmentByTag(tag)
        if (dialog != null && dialog is DialogFragment) {
            dialog.dismiss()
        }
    }

    @CallSuper
    protected open fun onPresenter(presenter: P?) {
        this.presenter = presenter
    }

    protected abstract fun getPresenterFactory() : PresenterFactory<P>
}