package ru.nobird.junction.base.presenter

import ru.nobird.junction.base.presenter.contract.ConnectionView


class ConnectionPresenter: PresenterBase<ConnectionView>() {
    companion object: PresenterFactory<ConnectionPresenter> {
        override fun create() = ConnectionPresenter()
    }

    override fun destroy() {}
}