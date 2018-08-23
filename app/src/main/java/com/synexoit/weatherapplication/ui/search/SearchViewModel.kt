package com.synexoit.weatherapplication.ui.search

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapplication.WeatherApplication
import com.synexoit.weatherapplication.data.entity.CityPlace
import com.synexoit.weatherapplication.data.entity.CityPreview
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.exceptions.Failure
import com.synexoit.weatherapplication.data.repository.CityPreviewRepository
import com.synexoit.weatherapplication.data.repository.CityRepository
import com.synexoit.weatherapplication.data.repository.GeocodeRepository
import com.synexoit.weatherapplication.data.repository.WeatherRepository
import com.synexoit.weatherapplication.data.util.Resource
import com.synexoit.weatherapplication.data.util.Status
import com.synexoit.weatherapplication.ui.base.BaseAndroidViewModel
import com.synexoit.weatherapplication.util.ListStatus
import com.synexoit.weatherapplication.util.ListWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val mWeatherRepository: WeatherRepository,
                                          private val mCityPreviewRepository: CityPreviewRepository,
                                          private val cityRepository: CityRepository,
                                          private val geocodeRepository: GeocodeRepository,
                                          application: WeatherApplication) : BaseAndroidViewModel(application) {

    companion object {
        const val GO_TO_MAIN_ACTIVITY = 1000
    }

    val cityList = MutableLiveData<ListWrapper<CityPreview>>()
    val isButtonVisible = MutableLiveData<Boolean>()

    init {
        getCityListFromDatabase()
    }

    private fun processResponse(response: Resource<City>, lastItemPosition: Int) {
        when (response.status) {
            is Status.Success -> {
                cityList.value?.let { wrapper ->
                    response.data?.run {
                        wrapper.list.add(CityPreview(name, address, placeId, lastItemPosition))
                    }
                    setCityPreviewListValue(ListWrapper(ListStatus.Refresh(), wrapper.list))
                }
            }

            is Status.Error -> handleFailure(Failure.UnknownAppError(response.message))
        }
    }

    fun getGeocodeCity(lat: Double, lng: Double, lastItemPosition: Int) {
        addDisposable(geocodeRepository.getGeocodeCityData(lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { getCity(it, lastItemPosition) },
                        { Timber.d("getGeocodeCity(): $it") })
        )
    }

    fun getCity(cityPlace: CityPlace, lastItemPosition: Int) {
        cityList.value?.run {
            val city = list.singleOrNull { it.placeId == cityPlace.id }
            city?.run {
                handleFailure(Failure.CityAlreadyInDatabaseException())
                return@getCity
            }
        }

        addDisposable(mWeatherRepository.getCity(cityPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { handleFailure(Failure.UnknownAppError(it.message)) }
                .subscribe { processResponse(it, lastItemPosition) })
    }

    private fun getCityListFromDatabase() {
        addDisposable(mCityPreviewRepository.getCityPreviewList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            setCityPreviewListValue(ListWrapper(ListStatus.New(),
                                    it.map { CityPreview(it.name, it.address, it.placeId, it.sortPosition) }
                                            .toMutableList()))
                        },
                        { handleFailure(Failure.UnknownAppError(it.message)) }
                ))
    }

    fun deleteCity(city: CityPreview) {
        addDisposable(mCityPreviewRepository.deleteCity(city.placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            cityList.value?.run {
                                list.remove(city)
                                setCityPreviewListValue(ListWrapper(ListStatus.Refresh(), list))
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

    fun startMainActivity() {
        onClickEvent.value = GO_TO_MAIN_ACTIVITY
    }
}