package com.lovers.app.applovers.presentation.di

import com.lovers.app.applovers.presentation.ui.login.LoginFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    fun inject(fragment: LoginFragment)

}