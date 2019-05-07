package com.lovers.app.applovers.presentation.ui.login

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v4.view.ViewCompat
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.lovers.app.applovers.App
import com.lovers.app.applovers.R
import com.lovers.app.applovers.presentation.presenters.login.ILoginPresenter
import com.lovers.app.applovers.presentation.ui.base.BaseFragment
import com.lovers.app.applovers.presentation.ui.welcome.WelcomeFragment
import com.lovers.app.applovers.utils.replace
import com.lovers.app.applovers.utils.setOnTextChangeListener
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import java.lang.NullPointerException

class LoginFragment : BaseFragment<ILoginPresenter.View, ILoginPresenter.Actions>(), ILoginPresenter.View {

    companion object {
        private const val TAG_INVALID_INPUT = "tag_invalid_input"
    }

    private val rootConstraintSet = ConstraintSet()
    private val loadingConstraintSet = ConstraintSet()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        exitTransition = Fade()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtTxtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        initListeners()
        setupConstraints()
    }

    override fun loginIsNotValid() {
        edtTxtLogin.backgroundResource = R.drawable.edit_text_error_background
        edtTxtLogin.tag = TAG_INVALID_INPUT
    }

    override fun passwordIsNotValid() {
        edtTxtPassword.backgroundResource = R.drawable.edit_text_error_background
        edtTxtPassword.tag = TAG_INVALID_INPUT
    }

    private fun initListeners() {
        btnPasswordVisibility.setOnClickListener {
            if (edtTxtPassword.transformationMethod is PasswordTransformationMethod) {
                edtTxtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                btnPasswordVisibility.imageResource = R.drawable.ic_eye_opened
            } else {
                edtTxtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                btnPasswordVisibility.imageResource = R.drawable.ic_eye_closed
            }

            edtTxtPassword.setSelection(edtTxtPassword.text.length)
        }

        edtTxtLogin.setOnTextChangeListener {
            if (edtTxtLogin.tag == TAG_INVALID_INPUT) {
                releaseLoginField()
            }
        }

        edtTxtPassword.setOnTextChangeListener {
            if (edtTxtPassword.tag == TAG_INVALID_INPUT) {
                releasePasswordField()
            }
        }

        btnLogin.setOnClickListener {
            releaseLoginField()
            releasePasswordField()
            presenter.login(edtTxtLogin.text.toString().trim(), edtTxtPassword.text.toString().trim())
        }

        logo.setOnClickListener {
            TransitionManager.beginDelayedTransition(root)
            rootConstraintSet.applyTo(root)
        }
    }

    private fun setupConstraints() {
        rootConstraintSet.clone(root)
        loadingConstraintSet.clone(activity, R.layout.fragment_login_loading)
    }

    private fun releaseLoginField() {
        edtTxtLogin.apply {
            tag = null
            backgroundResource = R.drawable.edit_text_background
        }
    }

    private fun releasePasswordField() {
        edtTxtPassword.apply {
            tag = null
            backgroundResource = R.drawable.edit_text_background
        }
    }

    override fun showLoading() {
        TransitionManager.beginDelayedTransition(root)
        loadingConstraintSet.applyTo(root)

        ObjectAnimator.ofInt(progress, "progress", 0, progress.max).apply {
            duration = 5000
            start()
        }
    }

    override fun navigateToWelcomeScreen() {
        fragmentManager?.replace(R.id.rootContainer, WelcomeFragment(), Fade(), logo)
    }

    override fun navigateToLoginScreen() {
        TransitionManager.beginDelayedTransition(root)
        rootConstraintSet.applyTo(root)
    }

    override fun inject() {
        App.instance.initLoginComponent()?.inject(this)
    }

    override fun onDetach() {
        super.onDetach()
        App.instance.destroyLoginComponent()
    }

    override fun layoutId() = R.layout.fragment_login
}