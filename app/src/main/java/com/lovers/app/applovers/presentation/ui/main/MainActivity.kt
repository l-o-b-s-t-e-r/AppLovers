package com.lovers.app.applovers.presentation.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.lovers.app.applovers.R
import com.lovers.app.applovers.presentation.ui.base.ActivityCallback
import com.lovers.app.applovers.presentation.ui.login.LoginFragment

class MainActivity : AppCompatActivity(), ActivityCallback {

    private var onBackClickTime = 0L
    private var currentFragment: Class<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootContainer, LoginFragment())
            .commit()
    }

    override fun onBackPressed() {
        if (currentFragment == LoginFragment::class.java) {
            if (onBackClickTime + 1000 > System.currentTimeMillis()) {
                super.onBackPressed()
            }
            onBackClickTime = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
    }

    override fun setCurrentFragmentClass(fragment: Class<Fragment>?) {
        currentFragment = fragment
    }
}
