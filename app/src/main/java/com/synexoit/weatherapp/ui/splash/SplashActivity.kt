package com.synexoit.weatherapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        startProperActivity()
    }

    /**
     * Starts proper activity based on current app state
     */
    private fun startProperActivity() {
        //TODO 25.04.2018 Dawid Jamroży change this
        val intent = if(true)
            Intent(this, SearchActivity::class.java)
        else
            Intent(this, MainActivity::class.java)

        navigator.startActivityAndFinishCurrent(intent)
    }
}