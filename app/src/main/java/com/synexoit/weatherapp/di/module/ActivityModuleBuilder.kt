package com.synexoit.weatherapp.di.module

import com.synexoit.weatherapp.ui.main.MainActivity
import com.synexoit.weatherapp.ui.main.MainModule
import com.synexoit.weatherapp.ui.splash.SplashActivity
import com.synexoit.weatherapp.ui.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModuleBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun contributeSplashActivity(): SplashActivity
}