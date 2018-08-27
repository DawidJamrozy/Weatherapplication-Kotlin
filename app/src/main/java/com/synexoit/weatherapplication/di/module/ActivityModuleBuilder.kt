package com.synexoit.weatherapplication.di.module

import com.synexoit.weatherapplication.data.module.LocationModule
import com.synexoit.weatherapplication.ui.main.MainActivity
import com.synexoit.weatherapplication.ui.main.MainModule
import com.synexoit.weatherapplication.ui.search.SearchActivity
import com.synexoit.weatherapplication.ui.search.SearchModule
import com.synexoit.weatherapplication.ui.settings.SettingsActivity
import com.synexoit.weatherapplication.ui.settings.SettingsModule
import com.synexoit.weatherapplication.ui.splash.SplashActivity
import com.synexoit.weatherapplication.ui.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModuleBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class, FragmentModuleBuilder::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [SearchModule::class, LocationModule::class])
    abstract fun contributeSearchActivity(): SearchActivity

    @ContributesAndroidInjector(modules = [SettingsModule::class, FragmentModuleBuilder::class])
    abstract fun contributeSettingsActivity(): SettingsActivity

}