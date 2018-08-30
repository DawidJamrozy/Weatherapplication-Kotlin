package com.synexoit.weatherapplication.ui.splash

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.synexoit.weatherapplication.cache.manager.SharedPreferencesManager
import com.synexoit.weatherapplication.data.extensions.failure
import com.synexoit.weatherapplication.data.extensions.getViewModel
import com.synexoit.weatherapplication.data.extensions.observe
import com.synexoit.weatherapplication.presentation.viewmodel.splash.SplashViewModel
import com.synexoit.weatherapplication.ui.base.navigator.Navigator
import com.synexoit.weatherapplication.ui.main.MainActivity
import com.synexoit.weatherapplication.ui.search.SearchActivity
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject

/**
 * Created by Dawid on 25.04.2018.
 */
class SplashActivity : AppCompatActivity() {

    companion object {
        const val UNIT = "unit"
        const val LANGUAGE = "language"
        const val AUTO = "auto"
    }

    @Inject
    protected lateinit var navigator: Navigator

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    protected lateinit var sharedPreferencesManager: SharedPreferencesManager

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(viewModelFactory) {
            observe(isCityTableEmpty, ::handleResponse)
            failure(failure, ::handleError)
        }

        setUnitAndLanguageSettings()
    }

    private fun setUnitAndLanguageSettings() {
        if (sharedPreferencesManager.getString(UNIT).isEmpty())
            sharedPreferencesManager.putValue(UNIT, AUTO)

        val language = sharedPreferencesManager.getString(LANGUAGE)
        val localeLanguage = Locale.getDefault().language
        if (language.isEmpty() || language != localeLanguage)
            sharedPreferencesManager.putValue(LANGUAGE, localeLanguage)
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
    private fun handleError(throwable: Throwable?) {
        navigator.startActivityAndFinishCurrent(Intent(this, SearchActivity::class.java))
    }
}