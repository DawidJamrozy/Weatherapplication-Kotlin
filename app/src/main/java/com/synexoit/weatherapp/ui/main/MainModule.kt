package com.synexoit.weatherapp.ui.main

import com.synexoit.weatherapp.ui.base.navigator.ANavigator
import com.synexoit.weatherapp.ui.base.navigator.Navigator
import dagger.Module
import dagger.Provides

/**
 * Created by Dawid on 25.04.2018.
 */
@Module
abstract class MainModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        internal fun provideNavigator(activity: MainActivity): Navigator {
            return ANavigator(activity)
        }
    }
}