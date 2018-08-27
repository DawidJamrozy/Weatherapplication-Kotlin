package com.synexoit.weatherapplication

import android.app.Application
import android.support.test.InstrumentationRegistry
import com.synexoit.weatherapplication.ui.base.BaseActivity
import org.junit.Rule

abstract class BaseAndroidTest<A : BaseActivity<*>>(clazz: Class<A>, initialTouchMode: Boolean = false,
                                                    launchActivity: Boolean = false) {

    protected val context = InstrumentationRegistry.getTargetContext()

    protected fun replaceApplicationTestComponent(application: WeatherApplication) {
        val applicationComponent = TestClient.obtainApplicationTestComponent(application)
        application.setApplicationComponent(applicationComponent)
    }

    @get:Rule open val activityTestRule
            = DaggerActivityTestRule(clazz, initialTouchMode, launchActivity, activityLaunchedListener())

    protected open fun activityLaunchedListener(): DaggerActivityTestRule.ActivityLaunchedListener<A> {
        return object : DaggerActivityTestRule.ActivityLaunchedListener<A> {
            override fun beforeActivityLaunched(application: Application, activity: A) {
                replaceApplicationTestComponent(application as WeatherApplication)
            }

            override fun afterActivityLaunched() {}
        }
    }
}