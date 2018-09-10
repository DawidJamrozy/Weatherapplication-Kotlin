@file:Suppress("IllegalIdentifier")

package com.synexoit.weatherapplication.splash

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.synexoit.weatherapplication.TestClient
import com.synexoit.weatherapplication.WeatherApplication
import com.synexoit.weatherapplication.data.repository.CityPreviewRepository
import com.synexoit.weatherapplication.data.repository.CityRepository
import com.synexoit.weatherapplication.ui.main.MainActivity
import com.synexoit.weatherapplication.ui.search.SearchActivity
import com.synexoit.weatherapplication.ui.splash.SplashActivity
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @get:Rule
    var rule = ActivityTestRule<SplashActivity>(SplashActivity::class.java,false ,false)

    private  val application: WeatherApplication = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as WeatherApplication

    private val context = InstrumentationRegistry.getTargetContext()

    @Inject
    lateinit var cityPreviewRepository: CityPreviewRepository

    @Inject
    lateinit var cityRepository: CityRepository

    @Before
    fun initSetup() {
        Intents.init()
        val testApplicationComponent = TestClient.obtainApplicationTestComponent(application)
        testApplicationComponent.inject(this)
        application.setApplicationComponent(testApplicationComponent)
    }

    @Test
    fun shouldStartSearchActivity() {
        whenever(cityPreviewRepository.getCityPreviewList()).thenReturn(Maybe.just(listOf()))
        whenever(cityPreviewRepository.isAnyCityInDatabase()).thenReturn(Single.just(true))

        rule.launchActivity(Intent(context, SplashActivity::class.java))

        intended(hasComponent(SearchActivity::class.java.name))
    }

    @Test
    fun shouldStartMainActivity() {
        whenever(cityRepository.getCityPlaceIdList()).thenReturn(Maybe.just(listOf()))
        whenever(cityPreviewRepository.isAnyCityInDatabase()).thenReturn(Single.just(false))

        rule.launchActivity(Intent(context, SplashActivity::class.java))

        intended(hasComponent(MainActivity::class.java.name))
    }

    @After
    fun clear() {
        Intents.release()
    }
}