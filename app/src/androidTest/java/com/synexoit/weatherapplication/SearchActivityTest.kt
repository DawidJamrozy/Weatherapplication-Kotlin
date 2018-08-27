package com.synexoit.weatherapplication

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.synexoit.weatherapplication.data.repository.CityPreviewRepository
import com.synexoit.weatherapplication.data.repository.CityRepository
import com.synexoit.weatherapplication.data.repository.GeocodeRepository
import com.synexoit.weatherapplication.data.repository.WeatherRepository
import com.synexoit.weatherapplication.ui.search.SearchActivity
import com.synexoit.weatherapplication.ui.search.SearchViewModel
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class SearchActivityTest : BaseAndroidTest<SearchActivity>(SearchActivity::class.java) {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var cityPreviewRepository: CityPreviewRepository

    @Inject
    lateinit var cityRepository: CityRepository

    @Inject
    lateinit var geocodeRepository: GeocodeRepository

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var application: WeatherApplication

    @Before
    fun initSetup() {
        application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as WeatherApplication
        testApplicationComponent = TestClient.obtainApplicationTestComponent(application)
        testApplicationComponent.inject(application)
        testApplicationComponent.injectt(this)
        application.setApplicationComponent(testApplicationComponent)

        /*    val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as WeatherApplication
            searchViewModel = SearchViewModel(weatherRepository, cityPreviewRepository, cityRepository,
                    geocodeRepository, app)*/
    }

    override fun createIntent(): Intent {
        return Intent(InstrumentationRegistry.getContext(), SearchActivity::class.java)
    }

    @Test
    fun dupa() {
        whenever(cityPreviewRepository.getCityPreviewList()).thenReturn(Maybe.just(listOf()))
        searchViewModel = SearchViewModel(weatherRepository, cityPreviewRepository, cityRepository,
                geocodeRepository, application)
        Timber.d("dupa(): ")
        //assert(!searchViewModel.isButtonVisible.value!!)
    }

    @Test
    fun dupa1() {
        Timber.d("dupa1(): ")
    }
}