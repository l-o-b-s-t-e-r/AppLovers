package com.lovers.app.applovers

import android.support.multidex.MultiDexApplication
import com.lovers.app.applovers.presentation.di.*

class App : MultiDexApplication() {

    companion object {
        lateinit var instance: App
    }

    private lateinit var appComponent: AppComponent
    private var loginComponent: LoginComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    fun initLoginComponent(): LoginComponent? {
        if (loginComponent == null) {
            loginComponent = appComponent.plusLoginComponent(LoginModule())
        }

        return loginComponent
    }

    fun destroyLoginComponent() {
        loginComponent = null
    }
}