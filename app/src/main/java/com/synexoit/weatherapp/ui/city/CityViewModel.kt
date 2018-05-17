package com.synexoit.weatherapp.ui.city

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.entity.DayDetails
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.entity.darksky.Currently
import com.synexoit.weatherapp.data.entity.darksky.DayData
import com.synexoit.weatherapp.data.exceptions.Failure
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.data.repository.WeatherRepository
import com.synexoit.weatherapp.ui.base.BaseAndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
class CityViewModel @Inject constructor(private val cityRepository: CityRepository,
                                        private val weatherRepository: WeatherRepository,
                                        weatherApplication: WeatherApplication) : BaseAndroidViewModel(weatherApplication) {

    companion object {
        const val OPEN_WEBSITE = 1000
        const val OPEN_SETTINGS = 1001
        const val DATE_FORMAT = "HH:mm dd.MM.yyyy"
        const val DAY_FORMAT = "EEEE"
    }

    val city = MutableLiveData<City>()
    val dayDetailsList = MutableLiveData<MutableList<DayDetails>>()
    val dayDataList = MutableLiveData<MutableList<DayData>>()
    val dataTime = MutableLiveData<String>()

    fun loadCityFromDatabase(placeId: String) {
        addDisposable(cityRepository.getCity(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //TODO 14.05.2018 by Dawid Jamroży
                .doOnError { handleFailure(Failure.UnknownAppError()) }
                .subscribe({ processResponse(it) }))
    }

    fun refreshWeatherData() {
        city.value?.let { city ->
            addDisposable(weatherRepository.getCity(city.toCityPlace())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    //TODO 14.05.2018 by Dawid Jamroży
                    .doOnError { handleFailure(Failure.UnknownAppError()) }
                    .subscribe({ processResponse(it.data) }))
        }
    }

    private fun processResponse(data: City?) {
        data?.let {
            dataTime.value = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.currently!!.time * 1000)
            city.value = it
            createDayData(it)
            createDayDetails(it.currently)
        }
    }

    private fun createDayData(city: City) {
        val temporaryDayDataList = mutableListOf<DayData>()
        val sdf = SimpleDateFormat(DAY_FORMAT, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone(city.timezone)
        city.daily?.data?.let { list ->
            list.take(7).forEach {
                val dayName = sdf.format(Date(it.time * 1000L))
                temporaryDayDataList.add(DayData(it.temperatureMin.toInt(),
                        it.temperatureMax.toInt(), it.icon, createDayName(dayName)))
            }
            dayDataList.value = temporaryDayDataList
        }
    }

    private fun createDayDetails(currently: Currently?) {
        currently?.let { nonNullCurrently ->
            val list = mutableListOf<DayDetails>()
            list.run {
                add(DayDetails(getString(R.string.wind), nonNullCurrently.windSpeed.toInt(), getString(R.string.speed_unit), R.drawable.wind))
                add(DayDetails(getString(R.string.humidity), (nonNullCurrently.humidity * 100).toInt(), getString(R.string.percent_unit), R.drawable.humidity))
                add(DayDetails(getString(R.string.apparent), nonNullCurrently.apparentTemperature.toInt(), getString(R.string.degree_unit), R.drawable.temperature))
                add(DayDetails(getString(R.string.precip), nonNullCurrently.precipIntensity.toInt(), getString(R.string.precip_unit), R.drawable.drop))
                add(DayDetails(getString(R.string.pressure), nonNullCurrently.pressure.toInt(), getString(R.string.pressure_unit), R.drawable.pressure))
            }

            dayDetailsList.value = list
        }
    }

    private fun createDayName(dayName: String) : String =
            dayName.substring(0, 1).toUpperCase() + dayName.substring(1)

    fun onSettingsClick() {
        onClickEvent.value = OPEN_SETTINGS
    }

    fun openWebsite()  {
        onClickEvent.value = OPEN_WEBSITE
    }
}