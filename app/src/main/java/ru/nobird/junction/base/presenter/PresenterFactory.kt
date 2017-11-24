package ru.nobird.junction.base.presenter

interface PresenterFactory<out P : Presenter<*>> {
    fun create() : P
}