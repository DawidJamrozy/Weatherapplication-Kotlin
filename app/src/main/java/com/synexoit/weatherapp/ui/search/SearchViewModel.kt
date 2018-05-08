package com.synexoit.weatherapp.ui.search

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.exceptions.CityAlreadyInDatabaseException
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.data.repository.WeatherRepository
import com.synexoit.weatherapp.ui.base.BaseAndroidViewModel
import com.synexoit.weatherapp.util.Resource
import com.synexoit.weatherapp.util.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val mWeatherRepository: WeatherRepository,
                                          private val mCityRepository: CityRepository,
                                          application: WeatherApplication) : BaseAndroidViewModel(application) {

    private val mCityList = MutableLiveData<MutableList<City>>()
    private val mCity = MutableLiveData<City>()

    init {
        getCityListFromDatabase()
    }

    private fun processResponse(response: Resource<City>) {
        when (response.status) {
            is Status.Success -> {
                mCityList.value?.add(response.data!!)
                mCity.value = response.data
            }
            is Status.Error -> Timber.d("processResponse(): ${response.message}")
        }
    }

    fun getCity(cityPlace: CityPlace) {
        mCityList.value?.let {
            for (city in it) {
                if (city.placeId == cityPlace.id) {
                    proceedWithError(CityAlreadyInDatabaseException(getApplication<WeatherApplication>().getString(R.string.error_city_already_in_database)))
                    return@getCity
                }
            }
        }
        addDisposable(mWeatherRepository.getCity(cityPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Timber.d("getCity(): error") }
                .subscribe({ processResponse(it) }))
    }

    private fun getCityListFromDatabase() {
        addDisposable(mCityRepository.getCityList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { mCityList.value = it.toMutableList() },
                        { Timber.d("getCityListFromDatabase(): ${it.message}") }
                ))
    }

    fun getCityListObserver() = mCityList

    fun getCityObserver() = mCity
}