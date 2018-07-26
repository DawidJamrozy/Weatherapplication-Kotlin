package com.synexoit.weatherapplication.di.module

import com.synexoit.weatherapplication.ui.city.CityFragment
import com.synexoit.weatherapplication.ui.city.CityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModuleBuilder {

    @ContributesAndroidInjector(modules = [CityModule::class])
    abstract fun contributeCityFragment(): CityFragment

}