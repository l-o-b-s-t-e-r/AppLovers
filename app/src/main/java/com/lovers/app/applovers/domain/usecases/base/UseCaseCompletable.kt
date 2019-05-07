package com.lovers.app.applovers.domain.usecases.base

import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class UseCaseCompletable <P : UseCaseParameters>() : DisposableHandler() {
    protected abstract fun buildUseCase(params: P): Completable

    fun execute(observer: CustomCompletableObserver = CustomCompletableObserver(), transformer: CompletableTransformer? = null, params: P = UseCaseParameters() as P) {
        var observable = buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        transformer?.let {
            observable = observable.compose(it)
        }

        addDisposable(observable.subscribeWith(observer))
    }

    fun execute(observer: CustomCompletableObserver = CustomCompletableObserver(), params: P = UseCaseParameters() as P) {
        execute(observer,null, params)
    }

    fun execute(transformer: CompletableTransformer? = null, params: P = UseCaseParameters() as P) {
        execute(CustomCompletableObserver(), transformer, params)
    }

    fun execute(params: P = UseCaseParameters() as P) {
        execute(CustomCompletableObserver(), null, params)
    }

    fun execute() {
        execute(CustomCompletableObserver(), null)
    }
}