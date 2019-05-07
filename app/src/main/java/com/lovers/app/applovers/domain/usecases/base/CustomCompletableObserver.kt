package com.lovers.app.applovers.domain.usecases.base

import io.reactivex.observers.DisposableCompletableObserver

open class CustomCompletableObserver : DisposableCompletableObserver() {

        override fun onComplete() {

        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

}