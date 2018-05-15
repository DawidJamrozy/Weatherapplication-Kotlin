package com.synexoit.weatherapp

import android.support.test.InstrumentationRegistry

abstract class BaseAndroidTest {

    protected val context = InstrumentationRegistry.getTargetContext()

    protected fun replaceApplicationTestComponent(application: WeatherApplication) {
        val applicationComponent = TestClient.obtainApplicationTestComponent(application)
        application.setApplicationComponent(applicationComponent)
    }

}