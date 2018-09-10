@file:Suppress("IllegalIdentifier")
package com.synexoit.weatherapplication.search

import android.content.Intent
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.synexoit.weatherapplication.BaseAndroidTest
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.TestClient
import com.synexoit.weatherapplication.data.entity.CityPreviewData
import com.synexoit.weatherapplication.data.entity.CurrentLocation
import com.synexoit.weatherapplication.data.entity.google.GeocodeData
import com.synexoit.weatherapplication.data.repository.*
import com.synexoit.weatherapplication.ui.main.MainActivity
import com.synexoit.weatherapplication.ui.search.SearchActivity
import com.synexoit.weatherapplication.utils.CityCreator
import com.synexoit.weatherapplication.utils.CityPreviewCreator
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class SearchActivityTest : BaseAndroidTest<SearchActivity>(SearchActivity::class.java) {

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)

    @Inject
    lateinit var cityPreviewRepository: CityPreviewRepository

    @Inject
    lateinit var cityRepository: CityRepository

    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var geocodeRepository: GeocodeRepository

    @Inject
    lateinit var weatherRepository: WeatherRepository

    override fun initSetup() {
        super.initSetup()
        val testApplicationComponent = TestClient.obtainApplicationTestComponent(application)
        testApplicationComponent.inject(this)
        application.setApplicationComponent(testApplicationComponent)
    }

    @Test
    fun shouldShowEmptyList() {
        whenever(cityPreviewRepository.getCityPreviewList()).thenReturn(Maybe.just(listOf()))

        launchActivity()

        assertRecyclerViewItemCount(R.id.recycler_view, 0)
        assertNotDisplayed(R.id.start_main_activity)
    }

    @Test
    fun shouldShowItemsInList() {
        whenever(cityPreviewRepository.getCityPreviewList())
                .thenReturn(Maybe.just(CityPreviewCreator.createCityPreviewDataList(4)))

        launchActivity()

        assertRecyclerViewItemCount(R.id.recycler_view, 4)
        assertDisplayed(R.id.start_main_activity)
    }

    @Test
    fun shouldStartMainActivityOnBottomButtonClick() {
        whenever(cityPreviewRepository.getCityPreviewList())
                .thenReturn(Maybe.just(CityPreviewCreator.createCityPreviewDataList(1)))
        whenever(cityRepository.getCityPlaceIdList()).thenReturn(Maybe.just(listOf()))

        launchActivity()

        assertRecyclerViewItemCount(R.id.recycler_view, 1)
        assertDisplayed(R.id.start_main_activity)
        clickOn(R.id.start_main_activity)

        wasActivityLaunched(MainActivity::class.java.name)
    }

    @Test
    fun shouldShowDialogToEnableUserLocation() {
        whenever(cityPreviewRepository.getCityPreviewList()).thenReturn(Maybe.just(listOf()))
        whenever(locationRepository.isLocationEnabled()).thenReturn(Single.just(false))

        launchActivity()

        assertRecyclerViewItemCount(R.id.recycler_view, 0)
        assertNotDisplayed(R.id.start_main_activity)
        clickOn(R.id.extra_button)

        assertDisplayed(context.getString(R.string.localization_dialog_title))
    }

    @Test
    fun shouldAddNewCityBasedOnProvidedLocalizationData() {
        val language = "pl"
        val currentLocation = CurrentLocation(20.0, 20.0)
        val geocodeData = GeocodeData("","", "")
        val city = CityCreator.createEmptyCity()

        whenever(cityPreviewRepository.getCityPreviewList()).thenReturn(Maybe.just(listOf()))
        whenever(locationRepository.isLocationEnabled()).thenReturn(Single.just(true))
        whenever(locationRepository.getUserLocation()).thenReturn(Single.just(currentLocation))
        whenever(geocodeRepository.getGeocodeCityData(currentLocation.lat, currentLocation.lng, language))
                .thenReturn(Single.just(geocodeData))
        whenever(weatherRepository.getCity(geocodeData.placeId))
                .thenReturn(Maybe.just(city))
        whenever(weatherRepository.createRemoteCall(currentLocation.lng, currentLocation.lng))
                .thenReturn(Maybe.just(city))

        launchActivity()

        assertRecyclerViewItemCount(R.id.recycler_view, 0)
        assertNotDisplayed(R.id.start_main_activity)
        clickOn(R.id.extra_button)

        assertRecyclerViewItemCount(R.id.recycler_view, 1)
        assertDisplayed(R.id.start_main_activity)
    }

    @Test
    fun shouldPreventFromAddingSameCitySecondTime() {
        val language = "pl"
        val currentLocation = CurrentLocation(20.0, 20.0)
        val geocodeData = GeocodeData("","", "")
        val city = CityCreator.createEmptyCity()

        whenever(cityPreviewRepository.getCityPreviewList())
                .thenReturn(Maybe.just(listOf(CityPreviewData("", "", "", 0))))

        whenever(locationRepository.isLocationEnabled()).thenReturn(Single.just(true))
        whenever(locationRepository.getUserLocation()).thenReturn(Single.just(currentLocation))
        whenever(geocodeRepository.getGeocodeCityData(currentLocation.lat, currentLocation.lng, language))
                .thenReturn(Single.just(geocodeData))
        whenever(weatherRepository.getCity(geocodeData.placeId))
                .thenReturn(Maybe.just(city))
        whenever(weatherRepository.createRemoteCall(currentLocation.lng, currentLocation.lng))
                .thenReturn(Maybe.just(city))

        launchActivity()

        assertRecyclerViewItemCount(R.id.recycler_view, 1)
        assertDisplayed(R.id.start_main_activity)
        clickOn(R.id.extra_button)

        assertRecyclerViewItemCount(R.id.recycler_view, 1)
    }

    @Test
    fun shouldDeleteCitiesFromListAndHideBottomButton() {
        whenever(cityPreviewRepository.getCityPreviewList())
                .thenReturn(Maybe.just(CityPreviewCreator.createCityPreviewDataList(2)))

        launchActivity()

        assertRecyclerViewItemCount(R.id.recycler_view, 2)
        assertDisplayed(R.id.start_main_activity)
        clickListItemChild(R.id.recycler_view, 1, R.id.remove)
        assertRecyclerViewItemCount(R.id.recycler_view, 1)
        assertDisplayed(R.id.start_main_activity)
        clickListItemChild(R.id.recycler_view, 0, R.id.remove)
        assertRecyclerViewItemCount(R.id.recycler_view, 0)
        assertNotDisplayed(R.id.start_main_activity)
    }

    private fun launchActivity() {
        rule.launchActivity(Intent(context, SearchActivity::class.java))
    }
}