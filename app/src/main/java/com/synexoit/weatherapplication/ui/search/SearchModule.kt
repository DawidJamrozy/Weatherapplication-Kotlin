package com.synexoit.weatherapplication.ui.search

import com.synexoit.weatherapplication.ui.base.navigator.ANavigator
import com.synexoit.weatherapplication.ui.base.navigator.Navigator
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