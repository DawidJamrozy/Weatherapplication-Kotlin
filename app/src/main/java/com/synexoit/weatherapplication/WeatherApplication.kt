package com.synexoit.weatherapplication

import android.app.Activity
import android.app.Application
import android.app.Service
import android.support.annotation.VisibleForTesting
import com.synexoit.weatherapplication.di.component.ApplicationComponent
import com.synexoit.weatherapplication.di.component.DaggerApplicationComponent
import com.synexoit.weatherapplication.di.injector.AppInjector
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

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .application(this)
                .context(this)
                .build()

        AppInjector.init(this, applicationComponent)

        initTimber()

        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                Timber.d("WeatherApplication caught UndeliverableException : ${e.localizedMessage}")
            }
        }
    }

    fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    @VisibleForTesting
    fun setApplicationComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
        AppInjector.init(this, applicationComponent)
    }

    override fun activityInjector(): AndroidInjector<Activity> = mDispatchingAndroidActivityInjector

    override fun serviceInjector(): AndroidInjector<Service> = mDispatchingAndroidServiceInjector
}