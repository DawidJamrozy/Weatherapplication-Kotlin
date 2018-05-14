package com.synexoit.weatherapp.ui.splash

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.synexoit.weatherapp.data.exceptions.Failure
import com.synexoit.weatherapp.data.extensions.failure
import com.synexoit.weatherapp.data.extensions.getViewModel
import com.synexoit.weatherapp.data.extensions.observe
import com.synexoit.weatherapp.ui.base.navigator.Navigator
import com.synexoit.weatherapp.ui.main.MainActivity
import com.synexoit.weatherapp.ui.search.SearchActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by Dawid on 25.04.2018.
 */
class SplashActivity : AppCompatActivity() {

    @Inject
    protected lateinit var navigator: Navigator

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(viewModelFactory, {
            observe(isCityTableEmpty, ::handleResponse)
            failure(failure, ::handleFailure)
        })
    }

    /**
     * Starts proper activity based on current app state
     */
    private fun handleResponse(isCityTableEmpty: Boolean?) {
        isCityTableEmpty?.let {
            val intent = if (it)
                Intent(this, SearchActivity::class.java)
            else
                Intent(this, MainActivity::class.java)

            navigator.startActivityAndFinishCurrent(intent)
        }
    }

    /**
     * Start SearchActivity in any case of error
     */
    private fun handleFailure(failure: Failure?) {
        navigator.startActivityAndFinishCurrent(Intent(this, SearchActivity::class.java))
    }
}