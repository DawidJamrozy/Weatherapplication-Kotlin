package com.synexoit.weatherapp.ui.city

import com.synexoit.weatherapp.ui.base.navigator.FNavigator
import com.synexoit.weatherapp.ui.base.navigator.FragmentNavigator
import dagger.Module
import dagger.Provides

/**
 * Created by Dawid on 05.05.2018.
 */
@Module
abstract class CityModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        internal fun provideNavigator(fragment: CityFragment): FragmentNavigator {
            return FNavigator(fragment)
        }
    }
}