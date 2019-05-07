package com.lovers.app.applovers.presentation.presenters.login

import com.lovers.app.applovers.presentation.presenters.base.IBasePresenter

interface ILoginPresenter {

    interface View : IBasePresenter.View {
        fun loginIsNotValid()

        fun passwordIsNotValid()

        fun navigateToWelcomeScreen()

        fun navigateToLoginScreen()
    }

    abstract class Actions : IBasePresenter.Actions<ILoginPresenter.View>() {
        abstract fun login(login: String, password: String)
    }

}