package com.synexoit.weatherapp.di.component

import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.di.module.ActivityModuleBuilder
import com.synexoit.weatherapp.di.module.ApplicationModule
import com.synexoit.weatherapp.di.module.ServiceModuleBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    ActivityModuleBuilder::class,
    ServiceModuleBuilder::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: WeatherApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: WeatherApplication)
}