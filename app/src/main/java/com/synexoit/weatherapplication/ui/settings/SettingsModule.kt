package com.synexoit.weatherapplication.ui.settings

import android.support.v4.app.FragmentActivity
import com.synexoit.weatherapplication.ui.base.navigator.ANavigator
import com.synexoit.weatherapplication.ui.base.navigator.Navigator
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class SettingsModule {

    @Binds
    abstract fun provideFragmentActivity(activity: SettingsActivity): FragmentActivity

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideNavigator(activity: SettingsActivity): Navigator {
            return ANavigator(activity)
        }
    }
}