package com.synexoit.weatherapplication

import com.synexoit.weatherapplication.di.component.TestApplicationComponent
import com.synexoit.weatherapplication.ui.base.BaseActivity

abstract class BaseAndroidTest<A : BaseActivity<*>>(clazz: Class<A>, initialTouchMode: Boolean = false,
                                                    launchActivity: Boolean = false) {

    lateinit var testApplicationComponent: TestApplicationComponent

    protected fun replaceApplicationTestComponent(application: WeatherApplication) {
        testApplicationComponent = TestClient.obtainApplicationTestComponent(application)
        testApplicationComponent.inject(application)
        application.setApplicationComponent(testApplicationComponent)
    }



}