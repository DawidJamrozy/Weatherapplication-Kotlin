package com.synexoit.weatherapp.di.module

import com.synexoit.weatherapp.ui.city.CityFragment
import com.synexoit.weatherapp.ui.city.CityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModuleBuilder {

    @ContributesAndroidInjector(modules = [CityModule::class])
    abstract fun contributeCityFragment(): CityFragment

}