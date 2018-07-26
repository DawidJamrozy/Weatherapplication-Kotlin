package com.synexoit.weatherapplication.di.component

import android.app.Application
import com.synexoit.weatherapplication.di.module.ActivityModuleBuilder
import com.synexoit.weatherapplication.di.module.TestApplicationModule
import com.synexoit.weatherapplication.di.module.ServiceModuleBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    TestApplicationModule::class,
    ActivityModuleBuilder::class,
    ServiceModuleBuilder::class])
interface TestApplicationComponent : ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
    }
}