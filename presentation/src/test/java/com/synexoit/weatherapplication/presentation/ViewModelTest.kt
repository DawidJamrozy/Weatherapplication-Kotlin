package com.synexoit.weatherapplication.presentation

import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.synexoit.weatherapplication.presentation.data.entity.MainState
import com.synexoit.weatherapplication.presentation.usecase.CityUseCase
import com.synexoit.weatherapplication.presentation.viewmodel.main.MainViewModel
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class ViewModelTest {

    private var cityUseCase: CityUseCase = mock()
    private val observerState = mock<Observer<MainState>>()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun initSetup() {
        mainViewModel = MainViewModel(cityUseCase)
    }

    @Test
    fun testTest() {
        val response = MainState(listOf())
        whenever(cityUseCase.getCityPlaceIdList()).thenReturn(Maybe.just(response))

        mainViewModel.cityIdList.observeForever(observerState)
        mainViewModel.loadCityIdListFromDatabase()

        val argumentCaptor = ArgumentCaptor.forClass(MainState::class.java)
        argumentCaptor.run {
            verify(observerState, times(1)).onChanged(capture())
            assertEquals(response, allValues[0])
        }
    }
}