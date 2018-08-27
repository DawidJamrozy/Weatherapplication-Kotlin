package com.synexoit.weatherapplication

import android.app.Application
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.synexoit.weatherapplication.ui.base.BaseActivity

class DaggerActivityTestRule<T : BaseActivity<*>>(activityClass: Class<T>, initialTouchMode: Boolean,
                                           launchActivity: Boolean,
                                           private val mListener: ActivityLaunchedListener<T>?)
    : ActivityTestRule<T>(activityClass, initialTouchMode, launchActivity) {

    constructor(activityClass: Class<T>, listener: ActivityLaunchedListener<T>?)
            : this(activityClass, false, listener)

    constructor(activityClass: Class<T>, initialTouchMode: Boolean, listener: ActivityLaunchedListener<T>?)
            : this(activityClass, initialTouchMode, true, listener)

    override fun beforeActivityLaunched() {
        mListener?.beforeActivityLaunched(InstrumentationRegistry.getInstrumentation()
                .targetContext.applicationContext as Application, activity)
    }

    override fun afterActivityLaunched() {
        mListener?.afterActivityLaunched()
    }

    override fun getActivityIntent(): Intent {
        return mListener!!.getActivityIntent()
    }


    interface ActivityLaunchedListener<T> {

        fun beforeActivityLaunched(application: Application, activity: T)

        fun afterActivityLaunched()

        fun getActivityIntent(): Intent
    }
}