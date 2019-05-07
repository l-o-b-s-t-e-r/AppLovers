package com.lovers.app.applovers.presentation.presenters.base

interface IBasePresenter {

    interface View {
        fun isNetworkAvailable(): Boolean

        fun showLoading()

        fun hideLoading()

        fun showToast(resId: Int)
    }

    open class Actions<T : IBasePresenter.View> {
        var view: T? = null

        fun bindView(view: T) {
            this.view = view
        }

        fun unbindView() {
            this.view = null
        }

        open fun dispose() {

        }
    }
}