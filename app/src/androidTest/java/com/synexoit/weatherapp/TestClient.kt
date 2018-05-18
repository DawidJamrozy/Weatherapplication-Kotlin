package com.synexoit.weatherapp

import android.app.Application
import com.synexoit.weatherapp.di.component.ApplicationComponent
import com.synexoit.weatherapp.di.component.DaggerTestApplicationComponent
import com.synexoit.weatherapp.di.component.TestApplicationComponent

object TestClient {

    private var testApplicationComponent : TestApplicationComponent? = null

    fun obtainApplicationTestComponent(application: Application): ApplicationComponent {
        val result = testApplicationComponent ?: DaggerTestApplicationComponent
                .builder()
                .application(application)
                .build()

        testApplicationComponent = result

        return result

   }
}