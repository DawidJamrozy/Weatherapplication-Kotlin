package com.synexoit.weatherapp.ui.search

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.repository.CityPlaceRepository
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.data.repository.WeatherRepository
import com.synexoit.weatherapp.ui.base.BaseAndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val mCityPlaceRepository: CityPlaceRepository,
                                          private val mWeatherRepository: WeatherRepository,
                                          private val mCityRepository: CityRepository,
                                          application: WeatherApplication) : BaseAndroidViewModel(application) {

    private val mCityPlaceList = MutableLiveData<MutableList<CityPlace>>()
    private val mCityPlace = MutableLiveData<CityPlace>()

    init {
        getCityPlacesFromDatabase()
    }


    fun insertCityToDatabase(city: City) {
        addDisposable(mCityRepository.insertCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.d("insertCityToDatabase: ")
                            getCityFromDatabase(it)
                        },
                        {
                            Timber.d("insertCityToDatabase: $it")
                        })
        )
    }

    fun getCityFromDatabase(id: Long) {
        addDisposable(mCityRepository.getCity(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Timber.d("getCityFromDatabase: $it") },
                        { Timber.d("getCityFromDatabase: $it") }
                ))
    }

    fun insertPlaceToDatabase(cityPlace: CityPlace) {
        addDisposable(mWeatherRepository.getCity(cityPlace.latitude, cityPlace.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            it.name = cityPlace.name
                            it.placeId = cityPlace.id
                            it.address = cityPlace.address
                            Timber.d("insertPlaceToDatabase: $it")
                            insertCityToDatabase(it)
                        },
                        { Timber.d("insertPlaceToDatabase: $it") }
                ))

        /* return
         if(!mCityPlaceList.value!!.contains(cityPlace)) {
             mCityPlace.value = cityPlace
             mCityPlaceList.value?.add(cityPlace)
             addDisposable(Completable.fromCallable { mCityPlaceRepository.insertCityPlace(cityPlace) }
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(
                             { Timber.d("insertPlaceToDatabase(): success") },
                             { Timber.d("insertPlaceToDatabase(): ${it.printStackTrace()}") }
                     ))
         } else
             proceedWithError(CityAlreadyInDatabaseException(getApplication<WeatherApplication>().getString(R.string.error_city_already_in_database)))*/
    }

    fun getCityPlacesFromDatabase() {
        addDisposable(mCityPlaceRepository.getAllCityPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { mCityPlaceList.value = it.toMutableList() },
                        { Timber.d("getCityPlacesFromDatabase(): ${it.printStackTrace()}") })
        )
    }

    fun getCityPlaceListObserver() = mCityPlaceList

    fun getCityPlaceObserver() = mCityPlace
}