package ru.nobird.junction.base.presenter

interface Presenter<in V> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun destroy()
}