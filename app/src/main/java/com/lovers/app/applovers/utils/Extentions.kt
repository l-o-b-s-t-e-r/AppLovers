package com.lovers.app.applovers.utils

import android.support.transition.ChangeBounds
import android.support.transition.TransitionSet
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun EditText.setOnTextChangeListener(action: () -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            action.invoke()
        }
    })
}

fun FragmentManager.replace(containerViewId: Int, fragment: Fragment, transition: Any, sharedElement: View? = null) {
    fragment.apply {
        enterTransition = transition
        sharedElementEnterTransition = TransitionSet().addTransition(ChangeBounds())
        sharedElementReturnTransition = TransitionSet().addTransition(ChangeBounds())
    }

    val transaction = beginTransaction()
        .replace(containerViewId, fragment, fragment::class.simpleName)
        .addToBackStack(fragment::class.simpleName)

    if (sharedElement != null) {
        transaction.addSharedElement(sharedElement, sharedElement.transitionName)
    }

    transaction.commitAllowingStateLoss()
}