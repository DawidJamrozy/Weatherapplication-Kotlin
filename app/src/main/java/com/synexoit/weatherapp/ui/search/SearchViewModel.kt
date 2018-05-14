package com.synexoit.weatherapp.ui.search

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.CityPreview
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.exceptions.Failure
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
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val mWeatherRepository: WeatherRepository,
                                          private val mCityPreviewRepository: CityPreviewRepository,
                                          private val cityRepository: CityRepository,
                                          application: WeatherApplication) : BaseAndroidViewModel(application) {

    val cityList = MutableLiveData<ListWrapper<CityPreview>>()
    val event = MutableLiveData<Int>()
    val isButtonVisible = MutableLiveData<Boolean>()

    init {
        getCityListFromDatabase()
    }

    private fun processResponse(response: Resource<City>) {
        when (response.status) {
            is Status.Success -> {
                cityList.value?.let { wrapper ->
                    response.data?.let {
                        wrapper.list.add(CityPreview(it.name, it.address, it.placeId))
                    }
                    setCityPreviewListValue(ListWrapper(ListStatus.Refresh(), wrapper.list))
                }
            }

            is Status.Error -> handleFailure(Failure.UnknownAppError(response.message))
        }
    }

    fun getCity(cityPlace: CityPlace) {
        cityList.value?.let {
            val city = it.list.singleOrNull { it.placeId == cityPlace.id }
            city?.let {
                handleFailure(Failure.CityAlreadyInDatabaseException())
                return@getCity
            }
        }

        addDisposable(mWeatherRepository.getCity(cityPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { handleFailure(Failure.UnknownAppError(it.message)) }
                .subscribe({ processResponse(it) }))
    }

    private fun getCityListFromDatabase() {
        addDisposable(mCityPreviewRepository.getCityPreviewList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { setCityPreviewListValue(ListWrapper(ListStatus.New(), it.toMutableList())) },
                        { handleFailure(Failure.UnknownAppError(it.message)) }
                ))
    }

    fun deleteCity(city: CityPreview) {
        addDisposable(mCityPreviewRepository.deleteCity(city.placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            cityList.value?.let {
                                it.list.remove(city)
                                setCityPreviewListValue(ListWrapper(ListStatus.Refresh(), it.list))
                            }
                        },
                        { handleFailure(Failure.UnknownAppError(it.message)) }
                ))
    }

    fun itemsMoved(fromPosition: Int, toPosition: Int) {
        cityList.value?.list?.let { list ->
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
                            { setCityPreviewListValue(ListWrapper(ListStatus.Refresh(), list)) },
                            { handleFailure(Failure.UnknownAppError(it.message)) }
                    ))
        }
    }

    private fun setCityPreviewListValue(wrapper: ListWrapper<CityPreview>) {
        isButtonVisible.value = wrapper.list.isEmpty()
        cityList.value = wrapper
    }

    //TODO 09.05.2018 by Dawid Jamroży notify to start main activity
    fun startMainActivity() {
        event.value = 1
    }
}