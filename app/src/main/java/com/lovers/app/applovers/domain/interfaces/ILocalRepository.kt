package com.lovers.app.applovers.domain.interfaces

import com.lovers.app.applovers.domain.entitites.LoginCredentials
import com.lovers.app.applovers.domain.entitites.User
import io.reactivex.Completable

interface ILocalRepository {

    fun save(user: User): Completable

}