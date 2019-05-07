package com.lovers.app.applovers.presentation.ui.base

import android.support.v4.app.Fragment

interface ActivityCallback {

    fun setCurrentFragmentClass(fragment: Class<Fragment>?)

}