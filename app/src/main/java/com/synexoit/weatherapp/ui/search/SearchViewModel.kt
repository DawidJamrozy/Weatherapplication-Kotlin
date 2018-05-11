package com.synexoit.weatherapp.ui.search

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.CityPreview
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.exceptions.CityAlreadyInDatabaseException
import com.synexoit.weatherapp.data.repository.CityPreviewRepository
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
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val mWeatherRepository: WeatherRepository,
                                          private val mCityPreviewRepository: CityPreviewRepository,
                                          private val cityRepository: CityRepository,
                                          application: WeatherApplication) : BaseAndroidViewModel(application) {

    private val mCityList = MutableLiveData<ListWrapper<CityPreview>>()
    private val event = MutableLiveData<Int>()
    private val isButtonVisible = MutableLiveData<Boolean>()

    init {
        getCityListFromDatabase()
    }

    private fun processResponse(response: Resource<City>) {
        when (response.status) {
            is Status.Success -> {
                mCityList.value?.let { wrapper ->
                    response.data?.let {
                        wrapper.list.add(CityPreview(it.name, it.address, it.placeId))
                    }
                    mCityList.value = ListWrapper(ListStatus.Refresh(), wrapper.list)
                    isButtonVisible.value = wrapper.list.isEmpty()
                }
            }
        //TODO 09.05.2018 Dawid Jamroży add throwable to response and notify ui to hide progress bar
            is Status.Error -> Timber.d("processResponse(): ${response.message}")
        }
    }

    fun getCity(cityPlace: CityPlace) {
        mCityList.value?.let {
            val city = it.list.singleOrNull { it.placeId == cityPlace.id }
            city?.let {
                proceedWithError(CityAlreadyInDatabaseException(getApplication<WeatherApplication>().getString(R.string.error_city_already_in_database)))
                return@getCity
            }
        }

        addDisposable(mWeatherRepository.getCity(cityPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { proceedWithError(it) }
                .subscribe({ processResponse(it) }))
    }

    private fun getCityListFromDatabase() {
        addDisposable(mCityPreviewRepository.getCityPreviewList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { mCityList.value = ListWrapper(ListStatus.New(), it.toMutableList()) },
                        { Timber.d("getCityListFromDatabase(): ${it.message}") }
                ))
    }

    fun deleteCity(city: CityPreview) {
        addDisposable(mCityPreviewRepository.deleteCity(city.placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            mCityList.value?.let {
                                it.list.remove(city)
                                mCityList.value = ListWrapper(ListStatus.Refresh(), it.list)
                                isButtonVisible.value = it.list.isEmpty()
                            }
                        },
                        { Timber.d("deleteCity(): ") }
                ))
    }

    fun itemsMoved(fromPosition: Int, toPosition: Int) {
        mCityList.value?.list?.let { list ->
            if (fromPosition < toPosition)
                for (i in fromPosition until toPosition) Collections.swap(list, i, i + 1)
            else
                for (i in fromPosition downTo toPosition + 1) Collections.swap(list, i, i - 1)

            val pairList = mutableListOf<Pair<String, Int>>()

            list.forEach {
                pairList.add(Pair(it.placeId, list.indexOf(it)))
            }

            addDisposable(cityRepository.changeItemsPosition(pairList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { mCityList.value = ListWrapper(ListStatus.Refresh(), list) },
                            { proceedWithError(it) }
                    ))
        }
    }

    //TODO 09.05.2018 by Dawid Jamroży notify to start main activity
    fun startMainActivity() {
        event.value = 1
    }

    fun getCityListObserver() = mCityList

    fun getEvent() = event

    fun isButtonVisible() = isButtonVisible
}