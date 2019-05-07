package com.lovers.app.applovers.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.lovers.app.applovers.presentation.presenters.base.IBasePresenter
import javax.inject.Inject
import android.net.ConnectivityManager


abstract class BaseFragment<V : IBasePresenter.View, P : IBasePresenter.Actions<V>> : Fragment(), IBasePresenter.View {

    @Inject
    lateinit var presenter: P

    private var callback: ActivityCallback? = null

    abstract fun inject()

    abstract fun layoutId(): Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
        if (context is ActivityCallback) {
            callback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callback?.setCurrentFragmentClass(this.javaClass)
        presenter.bindView(this as V)
    }

    override fun onDestroyView() {
        presenter.unbindView()
        presenter.dispose()
        callback?.setCurrentFragmentClass(null)
        super.onDestroyView()
    }

    override fun isNetworkAvailable(): Boolean {
        (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).let {
            return it.activeNetworkInfo != null && it.activeNetworkInfo.isConnected
        }
    }

    override fun showToast(resId: Int) {
        Toast.makeText(context, getString(resId), LENGTH_LONG).show()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}