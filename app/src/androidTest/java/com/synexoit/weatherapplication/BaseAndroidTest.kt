package com.synexoit.weatherapplication

import android.app.Application
import android.support.test.InstrumentationRegistry
import com.synexoit.weatherapplication.ui.base.BaseActivity
import org.junit.Rule

abstract class BaseAndroidTest<A : BaseActivity<*>>(clazz: Class<A>, initialTouchMode: Boolean = false,
                                                    launchActivity: Boolean = false) {

    protected val context = InstrumentationRegistry.getTargetContext()

    protected fun replaceApplicationTestComponent(application: Application) {
        val applicationComponent = TestClient.obtainApplicationTestComponent(application)
        (application as WeatherApplication).setApplicationComponent(applicationComponent)
    }

    @get:Rule
    protected open val activityTestRule
            = DaggerActivityTestRule(clazz, initialTouchMode, launchActivity, activityLaunchedListener())

    protected open fun activityLaunchedListener(): DaggerActivityTestRule.ActivityLaunchedListener<A> {
        return object : DaggerActivityTestRule.ActivityLaunchedListener<A> {
            override fun beforeActivityLaunched(application: Application, activity: A) {
                replaceApplicationTestComponent(application)
            }

            override fun afterActivityLaunched() {}
        }
    }
}