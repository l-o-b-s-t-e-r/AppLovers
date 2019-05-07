package com.lovers.app.applovers.data

import android.content.SharedPreferences
import com.lovers.app.applovers.domain.entitites.User
import com.lovers.app.applovers.domain.interfaces.ILocalRepository
import io.reactivex.Completable

class LocalRepository(val prefs: SharedPreferences) : ILocalRepository {

    companion object {
        const val USER_LOGIN = "user_login"
    }

    override fun save(user: User) =
        Completable.fromAction { prefs.edit().putString(USER_LOGIN, user.login).apply() }

}