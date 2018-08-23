package com.synexoit.weatherapplication.ui.search

import android.support.v4.app.FragmentActivity
import com.synexoit.weatherapplication.ui.base.navigator.ANavigator
import com.synexoit.weatherapplication.ui.base.navigator.Navigator
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class SearchModule {

    @Binds
    abstract fun provideFragmentActivity(activity: SearchActivity): FragmentActivity

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideNavigator(activity: SearchActivity): Navigator {
            return ANavigator(activity)
        }
    }
}