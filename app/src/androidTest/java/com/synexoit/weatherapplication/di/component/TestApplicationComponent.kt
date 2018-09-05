package com.synexoit.weatherapplication.di.component

import android.app.Application
import android.content.Context
import com.synexoit.weatherapplication.SplashActivityTest
import com.synexoit.weatherapplication.di.module.ActivityModuleBuilder
import com.synexoit.weatherapplication.di.module.TestApplicationModule
import com.synexoit.weatherapplication.search.SearchActivityTest
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
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): TestApplicationComponent
    }

    fun inject(searchActivity: SearchActivityTest)
    fun inject(splashActivity: SplashActivityTest)
}