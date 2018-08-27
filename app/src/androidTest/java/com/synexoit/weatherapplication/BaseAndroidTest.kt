package com.synexoit.weatherapplication

import android.app.Application
import android.content.Intent
import android.support.test.InstrumentationRegistry
import com.synexoit.weatherapplication.di.component.TestApplicationComponent
import com.synexoit.weatherapplication.ui.base.BaseActivity
import org.junit.Rule

abstract class BaseAndroidTest<A : BaseActivity<*>>(clazz: Class<A>, initialTouchMode: Boolean = false,
                                                    launchActivity: Boolean = false) {

    protected val context = InstrumentationRegistry.getTargetContext()

    lateinit var testApplicationComponent: TestApplicationComponent

    protected fun replaceApplicationTestComponent(application: WeatherApplication) {
        testApplicationComponent = TestClient.obtainApplicationTestComponent(application)
        application.setApplicationComponent(testApplicationComponent)
    }

    @Rule
    @JvmField
    val activityTestRule = DaggerActivityTestRule(clazz, initialTouchMode, launchActivity,
            object : DaggerActivityTestRule.ActivityLaunchedListener<A> {

                override fun beforeActivityLaunched(application: Application, activity: A) {
                    replaceApplicationTestComponent(application as WeatherApplication)
                }

                override fun afterActivityLaunched() {}

                override fun getActivityIntent(): Intent {
                    return createIntent()
                }
            })

    abstract fun createIntent(): Intent
}