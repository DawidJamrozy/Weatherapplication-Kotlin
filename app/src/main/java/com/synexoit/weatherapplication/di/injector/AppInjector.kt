package com.synexoit.weatherapplication.di.injector

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.synexoit.weatherapplication.WeatherApplication
import com.synexoit.weatherapplication.di.Injectable
import com.synexoit.weatherapplication.di.component.ApplicationComponent
import com.synexoit.weatherapplication.util.SimpleActivityLifecycleCallbacks
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * App injector
 */
class AppInjector {

    companion object {

        fun init(application: WeatherApplication, applicationComponent: ApplicationComponent) {
            applicationComponent.inject(application)

            application.registerActivityLifecycleCallbacks(object : SimpleActivityLifecycleCallbacks() {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }
            })
        }

        private fun handleActivity(activity: Activity) {
            if (activity is HasSupportFragmentInjector) AndroidInjection.inject(activity)

            (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentPreAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true)
        }
    }
}