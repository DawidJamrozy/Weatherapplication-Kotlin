package com.synexoit.weatherapplication

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.rule.ActivityTestRule
import com.synexoit.weatherapplication.ui.base.BaseActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseAndroidTest<A : BaseActivity<*>>(clazz: Class<A>, initialTouchMode: Boolean = false,
                                                    launchActivity: Boolean = false) {

    @get:Rule
    var rule = ActivityTestRule<A>(clazz, initialTouchMode, launchActivity)

    protected val mContext = InstrumentationRegistry.getTargetContext()

    protected val application: WeatherApplication = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as WeatherApplication

    fun wasActivityLaunched(className: String) {
        Intents.intended(IntentMatchers.hasComponent(className))
    }

    @Before
    open fun initSetup() {
        Intents.init()
    }

    @After
    fun clear() {
        Intents.release()
    }

}