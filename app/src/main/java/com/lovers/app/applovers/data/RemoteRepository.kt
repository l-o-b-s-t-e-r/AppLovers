package com.lovers.app.applovers.data

import com.lovers.app.applovers.domain.entitites.LoginCredentials
import com.lovers.app.applovers.domain.interfaces.IRemoteRepository

class RemoteRepository(private val api: Api) : IRemoteRepository {

    override fun login(credentials: LoginCredentials) = api.login(credentials)

}