package com.synexoit.weatherapplication.ui.splash

import android.support.v4.app.FragmentActivity
import com.synexoit.weatherapplication.ui.base.navigator.ANavigator
import com.synexoit.weatherapplication.ui.base.navigator.Navigator
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Created by Dawid on 25.04.2018.
 */
@Module
abstract class SplashModule {

    @Binds
    abstract fun provideFragmentActivity(activity: SplashActivity): FragmentActivity

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideNavigator(activity: SplashActivity): Navigator {
            return ANavigator(activity)
        }
    }
}