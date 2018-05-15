package com.synexoit.weatherapp

import com.synexoit.weatherapp.di.component.ApplicationComponent
import com.synexoit.weatherapp.di.component.ApplicationTestComponent
import com.synexoit.weatherapp.di.component.DaggerApplicationTestComponent

object TestClient {

    private var applicationTestComponent : ApplicationTestComponent? = null

    fun obtainApplicationTestComponent(application: WeatherApplication): ApplicationComponent {
        val result = applicationTestComponent ?: DaggerApplicationTestComponent
                .builder()
                .application(application)
                .build()

        applicationTestComponent = result

        return result

   }
}