package com.synexoit.weatherapp

import android.app.Activity
import android.app.Application
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule

class DaggerActivityTestRule<T : Activity>(activityClass: Class<T>, initialTouchMode: Boolean,
                                           launchActivity: Boolean,
                                           private val mListener: ActivityLaunchedListener<T>?) : ActivityTestRule<T>(activityClass, initialTouchMode, launchActivity) {

    constructor(activityClass: Class<T>,
                listener: ActivityLaunchedListener<T>?) : this(activityClass, false, listener)

    constructor(activityClass: Class<T>, initialTouchMode: Boolean,
                listener: ActivityLaunchedListener<T>?) : this(activityClass, initialTouchMode, true, listener)

    override fun beforeActivityLaunched() {
        mListener?.beforeActivityLaunched(InstrumentationRegistry.getInstrumentation()
                .targetContext.applicationContext as Application, activity)
    }

    override fun afterActivityLaunched() {
        mListener?.afterActivityLaunched()
    }

    interface ActivityLaunchedListener<T> {

        fun beforeActivityLaunched(application: Application, activity: T)

        fun afterActivityLaunched()
    }
}