package com.lovers.app.applovers.presentation.ui.login

import android.support.v4.util.PatternsCompat.EMAIL_ADDRESS
import android.util.Log
import com.lovers.app.applovers.R
import com.lovers.app.applovers.domain.usecases.LoginUseCase
import com.lovers.app.applovers.domain.usecases.base.CustomCompletableObserver
import com.lovers.app.applovers.presentation.presenters.login.ILoginPresenter
import com.lovers.app.applovers.utils.RxUtils
import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.CompletableTransformer
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import java.util.regex.Pattern
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val loginUseCase: LoginUseCase) : ILoginPresenter.Actions() {

    override fun login(login: String, password: String) {
        if (loginIsValid(login) && passwordIsValid(password)) {
            loginUseCase.execute(object : CustomCompletableObserver() {
                override fun onComplete() {
                    view?.navigateToWelcomeScreen()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    when (e) {
                        is HttpException -> {
                            when (e.code()) {
                                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                                    view?.loginIsNotValid()
                                    view?.passwordIsNotValid()
                                    view?.showToast(R.string.toast_email_or_password_is_incorrect)
                                }
                                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                                    view?.showToast(R.string.toast_server_error)
                                }
                                else -> {
                                    view?.showToast(R.string.toast_unrecognized_error)
                                }
                            }
                        }
                        is UnknownHostException -> {
                            if (view?.isNetworkAvailable() == true) {
                                view?.showToast(R.string.toast_unrecognized_error)
                            } else {
                                view?.showToast(R.string.toast_no_internet)
                            }
                        }
                        else -> {
                            view?.showToast(R.string.toast_unrecognized_error)
                        }
                    }

                    view?.navigateToLoginScreen()
                }
            }, RxUtils.applyLoadingSchedulersCompl(view), LoginUseCase.Params(login, password))
        }
    }

    private fun loginIsValid(login: String): Boolean {
        if (login.isEmpty()) {
            view?.showToast(R.string.toast_field_is_empty)
            view?.loginIsNotValid()
            return false
        }

        if (!EMAIL_ADDRESS.matcher(login).matches()) {
            view?.showToast(R.string.toast_email_is_not_valid)
            view?.loginIsNotValid()
            return false
        }

        return true
    }

    private fun passwordIsValid(password: String): Boolean {
        if (password.isEmpty()) {
            view?.showToast(R.string.toast_field_is_empty)
            view?.passwordIsNotValid()
            return false
        }

        if (Pattern.compile(" ").matcher(password).find()) {
            view?.showToast(R.string.toast_illegal_character)
            view?.passwordIsNotValid()
            return false
        }

        return true
    }

    override fun dispose() {
        loginUseCase.dispose()
    }
}