package com.lovers.app.applovers.domain.usecases

import com.lovers.app.applovers.domain.entitites.LoginCredentials
import com.lovers.app.applovers.domain.entitites.User
import com.lovers.app.applovers.domain.interfaces.ILocalRepository
import com.lovers.app.applovers.domain.interfaces.IRemoteRepository
import com.lovers.app.applovers.domain.usecases.base.UseCaseCompletable
import com.lovers.app.applovers.domain.usecases.base.UseCaseParameters
import io.reactivex.Completable
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val localRepository: ILocalRepository, private val remoteRepository: IRemoteRepository) :
    UseCaseCompletable<LoginUseCase.Params>() {

    override fun buildUseCase(params: Params) =
        remoteRepository.login(LoginCredentials(params.login, params.password))
            .flatMapCompletable {
                localRepository.save(User(params.login))
            }

    class Params(val login: String, val password: String) : UseCaseParameters()
}