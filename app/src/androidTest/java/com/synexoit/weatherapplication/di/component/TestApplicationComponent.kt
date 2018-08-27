package com.synexoit.weatherapplication.di.component

import android.content.Context
import com.synexoit.weatherapplication.WeatherApplication
import com.synexoit.weatherapplication.di.module.ActivityModuleBuilder
import com.synexoit.weatherapplication.di.module.TestApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    TestApplicationModule::class,
    ActivityModuleBuilder::class])
interface TestApplicationComponent : ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: WeatherApplication): Builder

        @BindsInstance
        fun context(application: Context): Builder

        fun build(): TestApplicationComponent
    }
}