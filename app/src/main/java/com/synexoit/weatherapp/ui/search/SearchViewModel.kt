package com.synexoit.weatherapp.ui.search

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.exceptions.CityAlreadyInDatabaseException
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.data.repository.WeatherRepository
import com.synexoit.weatherapp.ui.base.BaseAndroidViewModel
import com.synexoit.weatherapp.util.ListStatus
import com.synexoit.weatherapp.util.ListWrapper
import com.synexoit.weatherapp.util.Resource
import com.synexoit.weatherapp.util.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val mWeatherRepository: WeatherRepository,
                                          private val mCityRepository: CityRepository,
                                          application: WeatherApplication) : BaseAndroidViewModel(application) {

    private val mCityList = MutableLiveData<ListWrapper<City>>()
    private val event = MutableLiveData<Int>()

    init {
        getCityListFromDatabase()
    }

    private fun processResponse(response: Resource<City>) {
        when (response.status) {
            is Status.Success -> {
                mCityList.value?.let {
                    it.list.add(response.data!!)
                    mCityList.value = ListWrapper(ListStatus.Refresh(), it.list)
                }
            }
            is Status.Error -> Timber.d("processResponse(): ${response.message}")
        }
    }

    fun getCity(cityPlace: CityPlace) {
        mCityList.value?.let {
            for (city in it.list) {
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
                        { mCityList.value = ListWrapper(ListStatus.New(), it.toMutableList()) },
                        { Timber.d("getCityListFromDatabase(): ${it.message}") }
                ))
    }

    //TODO 09.05.2018 by Dawid Jamroży
    fun startMainActivity(view: View) {
        event.value = 1
    }

    fun deleteCity(city: City) {
        addDisposable(mCityRepository.removeCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            mCityList.value?.let {
                                it.list.remove(city)
                                mCityList.value = ListWrapper(ListStatus.Refresh(), it.list)
                            }
                        },
                        { Timber.d("deleteCity(): ") }
                ))
    }

    fun getCityListObserver() = mCityList

    fun getEvent() = event
}