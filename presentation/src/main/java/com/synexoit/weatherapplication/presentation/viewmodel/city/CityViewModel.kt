package com.synexoit.weatherapplication.presentation.viewmodel.city

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.exceptions.Failure
import com.synexoit.weatherapplication.presentation.data.entity.CityPlace
import com.synexoit.weatherapplication.presentation.usecase.CityUseCase
import com.synexoit.weatherapplication.presentation.usecase.WeatherUseCase
import com.synexoit.weatherapplication.presentation.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
class CityViewModel @Inject constructor(private val cityUseCase: CityUseCase,
                                        private val weatherUseCase: WeatherUseCase) : BaseViewModel() {

    companion object {
        const val OPEN_WEBSITE = 1000
        const val OPEN_SETTINGS = 1001
        private const val DATE_FORMAT = "HH:mm dd.MM.yyyy"
    }

    val city = MutableLiveData<City>()
    val dataTime = MutableLiveData<String>()

    fun loadCityFromDatabase(placeId: String) {
        addDisposable(cityUseCase.getCity(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //TODO 14.05.2018 by Dawid Jamroży
                .doOnError { handleFailure(Failure.UnknownAppError()) }
                .subscribe { processResponse(it) })
    }

    fun refreshWeatherData() {
        city.value?.let { city ->
            addDisposable(weatherUseCase.getCity(CityPlace.from(city))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    //TODO 14.05.2018 by Dawid Jamroży
                    .doOnError { handleFailure(Failure.UnknownAppError()) }
                    .subscribe { processResponse(it.data) })
        }
    }

    private fun processResponse(data: City?) {
        data?.let {
            dataTime.value = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.currently.time * 1000)
            city.value = it
        }
    }

    fun onSettingsClick() {
        onClickEvent.value = OPEN_SETTINGS
    }

    fun openWebsite()  {
        onClickEvent.value = OPEN_WEBSITE
    }
}