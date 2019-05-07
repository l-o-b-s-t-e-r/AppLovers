package com.lovers.app.applovers.presentation.di

import com.lovers.app.applovers.presentation.ui.login.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    fun plusLoginComponent(module: LoginModule): LoginComponent

}