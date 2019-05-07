package com.lovers.app.applovers.domain.interfaces

import com.lovers.app.applovers.domain.entitites.LoginCredentials
import com.lovers.app.applovers.domain.entitites.LoginResponse
import io.reactivex.Completable
import io.reactivex.Single

interface IRemoteRepository {

    fun login(credentials: LoginCredentials): Single<LoginResponse>

}