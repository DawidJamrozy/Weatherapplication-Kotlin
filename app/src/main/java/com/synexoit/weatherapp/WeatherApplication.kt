package com.synexoit.weatherapp

import android.app.Activity
import android.app.Application
import android.app.Service
import com.synexoit.weatherapp.di.injector.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject

class WeatherApplication : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var mDispatchingAndroidActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mDispatchingAndroidServiceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        Timber.plant(Timber.DebugTree())

        RxJavaPlugins.setErrorHandler({ e ->
            if (e is UndeliverableException) {
                Timber.d("ChatApplication caught UndeliverableException : ${e.localizedMessage}")
            }
        })
    }

    override fun activityInjector(): AndroidInjector<Activity> = mDispatchingAndroidActivityInjector

    override fun serviceInjector(): AndroidInjector<Service> = mDispatchingAndroidServiceInjector
}