package com.lovers.app.applovers.utils

import com.lovers.app.applovers.presentation.presenters.base.IBasePresenter
import io.reactivex.CompletableTransformer

object RxUtils {

    fun <V : IBasePresenter.View> applyLoadingSchedulersCompl(view: V?): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.doOnSubscribe { view?.showLoading() }
                .doFinally { view?.hideLoading() }
        }
    }

}