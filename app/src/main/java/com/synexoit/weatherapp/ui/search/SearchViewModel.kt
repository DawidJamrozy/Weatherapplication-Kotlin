package com.synexoit.weatherapp.ui.search

import android.arch.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.PublishRelay
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.exceptions.CityAlreadyInDatabaseException
import com.synexoit.weatherapp.data.model.CityPlace
import com.synexoit.weatherapp.data.repository.CityPlaceRepository
import com.synexoit.weatherapp.ui.base.BaseAndroidViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val mCityPlaceRepository: CityPlaceRepository,
                                          application: WeatherApplication) : BaseAndroidViewModel(application) {

    private val relay = PublishRelay.create<String>()

    private val mCityPlaceList = MutableLiveData<MutableList<CityPlace>>()
    private val mCityPlace = MutableLiveData<CityPlace>()

    init {
        getCityPlacesFromDatabase()
    }

    //TODO 26.04.2018 by Dawid Jamroży remove this later
   /* fun onTextChanged(text: CharSequence) {
        relay.accept(text.toString())
    }

    private fun registerRelay() {
        addDisposable(relay.filter { it.isNotEmpty() }
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Timber.d("registerRelay(): $it") },
                        { it.printStackTrace() }
                ))
    }*/

    fun insertPlaceToDatabase(cityPlace: CityPlace) {
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
            proceedWithError(CityAlreadyInDatabaseException(getApplication<WeatherApplication>().getString(R.string.error_city_already_in_database)))
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