package com.synexoit.weatherapp

import android.app.Application
import android.support.test.InstrumentationRegistry

abstract class BaseAndroidTest {

    protected val context = InstrumentationRegistry.getTargetContext()

    protected fun replaceApplicationTestComponent(application: Application) {
        val applicationComponent = TestClient.obtainApplicationTestComponent(application)
        (application as WeatherApplication).setApplicationComponent(applicationComponent)
    }
}