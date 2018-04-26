package com.synexoit.weatherapp.ui.search

import com.synexoit.weatherapp.ui.base.navigator.ANavigator
import com.synexoit.weatherapp.ui.base.navigator.Navigator
import dagger.Module
import dagger.Provides

@Module
abstract class SearchModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        internal fun provideNavigator(activity: SearchActivity): Navigator {
            return ANavigator(activity)
        }
    }
}