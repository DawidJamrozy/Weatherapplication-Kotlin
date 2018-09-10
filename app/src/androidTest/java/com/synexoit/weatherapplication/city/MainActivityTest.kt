package com.synexoit.weatherapplication.city

import android.content.Intent
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.whenever
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import com.synexoit.weatherapplication.BaseAndroidTest
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.TestClient
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.repository.CityRepository
import com.synexoit.weatherapplication.ui.main.MainActivity
import com.synexoit.weatherapplication.utils.CustomMatcher.Companion.checkAtPosition
import io.reactivex.Maybe
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseAndroidTest<MainActivity>(MainActivity::class.java) {

    @Inject
    lateinit var cityRepository: CityRepository
    
    @Inject
    lateinit var objectMapper: ObjectMapper

    override fun initSetup() {
        super.initSetup()
        val testApplicationComponent = TestClient.obtainApplicationTestComponent(application)
        testApplicationComponent.inject(this)
        application.setApplicationComponent(testApplicationComponent)
    }

    @Test
    fun testCityFragmentView() {
        val cracowPlaceId = "ChIJ0RhONcBEFkcRv4pHdrW2a7Q"

        val city = objectMapper.readValue<City>(context.resources.openRawResource(R.raw.city_test_object),
                object : TypeReference<City>() {})

        whenever(cityRepository.getCityPlaceIdList()).thenReturn(Maybe.just(listOf(cracowPlaceId)))
        whenever(cityRepository.getCity(cracowPlaceId)).thenReturn(Maybe.just(city))

        launchActivity()

        assertDisplayed(R.id.address, city.address)
        assertDisplayed(R.id.temperature, context.getString(R.string.degree, city.currently.toIntTemperature()))
        assertDisplayed(R.id.name, context.getString(R.string.vertical_line, city.name, city.currently.summary))
        assertDisplayed(R.string.weekly)
        assertDisplayed(R.id.floatingActionButton)

        scrollTo(R.id.day_recycler_view)
        assertRecyclerViewItemCount(R.id.day_recycler_view, 7)

        checkAtPosition(R.id.day_recycler_view, 0, hasDescendant(withText("Poniedziałek")))
        checkAtPosition(R.id.day_recycler_view, 1, hasDescendant(withText("Wtorek")))
        checkAtPosition(R.id.day_recycler_view, 2, hasDescendant(withText("Środa")))
        checkAtPosition(R.id.day_recycler_view, 3, hasDescendant(withText("Czwartek")))
        checkAtPosition(R.id.day_recycler_view, 4, hasDescendant(withText("Piątek")))
        checkAtPosition(R.id.day_recycler_view, 5, hasDescendant(withText("Sobota")))
        checkAtPosition(R.id.day_recycler_view, 6, hasDescendant(withText("Niedziela")))

        scrollTo(R.string.hourly)
        assertDisplayed(R.string.hourly)

        scrollTo(R.string.details)
        assertDisplayed(R.string.details)

        scrollTo(R.id.details_recycler_view)
        assertRecyclerViewItemCount(R.id.details_recycler_view, 5)

        val currently = city.currently

        checkRecyclerViewDetailsItem(0, R.string.wind, currently.windSpeed.toInt(), R.string.speed_unit)
        checkRecyclerViewDetailsItem(1, R.string.humidity, (currently.humidity * 100).toInt(), R.string.percent_unit)
        checkRecyclerViewDetailsItem(2, R.string.apparent, currently.apparentTemperature.toInt(), R.string.degree_unit)
        checkRecyclerViewDetailsItem(3, R.string.precip, currently.precipIntensity.toInt(), R.string.precip_unit)
        checkRecyclerViewDetailsItem(4, R.string.pressure, currently.pressure.toInt(), R.string.pressure_unit)
    }

    private fun checkRecyclerViewDetailsItem(position: Int, mainTextStringRes: Int, value: Int, unitResId: Int) {
        checkAtPosition(R.id.details_recycler_view, position, hasDescendant(withText(context.getString(mainTextStringRes))))
        checkAtPosition(R.id.details_recycler_view, position,
                hasDescendant(withText(context.getString(R.string.day_details, value, context.getString(unitResId)))))
    }

    private fun launchActivity() {
        rule.launchActivity(Intent(context, MainActivity::class.java))
    }
}