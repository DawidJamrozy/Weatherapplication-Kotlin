package com.synexoit.weatherapplication.ui.city

import android.support.v4.app.Fragment
import com.synexoit.weatherapplication.ui.base.navigator.FNavigator
import com.synexoit.weatherapplication.ui.base.navigator.FragmentNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Created by Dawid on 05.05.2018.
 */
@Module
abstract class CityModule {

    @Binds
    abstract fun provideFragment(fragment: CityFragment): Fragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideNavigator(fragment: CityFragment): FragmentNavigator {
            return FNavigator(fragment)
        }
    }
}