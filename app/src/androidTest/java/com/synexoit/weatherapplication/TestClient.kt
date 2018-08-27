package com.synexoit.weatherapplication

import com.synexoit.weatherapplication.di.component.ApplicationComponent
import com.synexoit.weatherapplication.di.component.DaggerTestApplicationComponent
import com.synexoit.weatherapplication.di.component.TestApplicationComponent

object TestClient {

    private var testApplicationComponent : TestApplicationComponent? = null

    fun obtainApplicationTestComponent(application: WeatherApplication): ApplicationComponent {
        val result = testApplicationComponent ?: DaggerTestApplicationComponent
                .builder()
                .application(application)
                .build()

        testApplicationComponent = result

        return result

   }
}