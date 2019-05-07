package com.lovers.app.applovers.presentation.di

import com.lovers.app.applovers.domain.usecases.LoginUseCase
import com.lovers.app.applovers.presentation.presenters.login.ILoginPresenter
import com.lovers.app.applovers.presentation.ui.login.LoginPresenter
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    @FragmentScope
    fun providePresenter(loginUseCase: LoginUseCase): ILoginPresenter.Actions = LoginPresenter(loginUseCase)

}