package com.synexoit.weatherapplication

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.*
import com.synexoit.weatherapplication.presentation.data.entity.MainState
import com.synexoit.weatherapplication.presentation.usecase.CityUseCase
import com.synexoit.weatherapplication.presentation.viewmodel.main.MainViewModel
import com.synexoit.weatherapplication.ui.search.SearchActivity
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class SearchActivityTest : BaseAndroidTest<SearchActivity>(SearchActivity::class.java) {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var application: WeatherApplication
    private val observerState = mock<Observer<MainState>>()

    @Mock
    lateinit var cityUseCase: CityUseCase

  /*  @Inject
    lateinit var cityRepository: CityRepository*/

    @Captor
    private lateinit var captor: KArgumentCaptor<List<String>>
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun initSetup() {
        application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as WeatherApplication
        val testApplicationComponent = TestClient.obtainApplicationTestComponent(application)
        testApplicationComponent.inject(this)
        application.setApplicationComponent(testApplicationComponent)

        captor = argumentCaptor()
        cityUseCase = mock()
        mainViewModel = MainViewModel(cityUseCase)
    }

    @Test
    fun viewModelExecutesUseCase() {
        val response = MainState(listOf())
        whenever(cityUseCase.dupa()).thenReturn(Maybe.just(response))

        mainViewModel.loadCityIdListFromDatabase()

        verify(cityUseCase, times(1)).dupa()
    }

    @Test
    fun dupeczka() {
        val response = MainState(listOf())
        whenever(cityUseCase.dupa()).thenReturn(Maybe.just(response))

        mainViewModel.cityIdList.observeForever(observerState)
        mainViewModel.loadCityIdListFromDatabase()


    }
}