package com.lovers.app.applovers.data

import com.lovers.app.applovers.domain.entitites.LoginCredentials
import com.lovers.app.applovers.domain.entitites.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("login")
    fun login(@Body credentials: LoginCredentials): Single<LoginResponse>

}