package com.synexoit.weatherapplication

import android.app.Application
import android.support.test.runner.AndroidJUnit4
import com.synexoit.weatherapplication.ui.search.SearchActivity
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchActivityTest : BaseAndroidTest() {

    @get:Rule
    val activityTestRule = DaggerActivityTestRule(SearchActivity::class.java, false, false,
            object : DaggerActivityTestRule.ActivityLaunchedListener<SearchActivity> {
                override fun beforeActivityLaunched(application: Application, activity: SearchActivity) {
                  replaceApplicationTestComponent(application)
                }

                override fun afterActivityLaunched() {}
            })

}