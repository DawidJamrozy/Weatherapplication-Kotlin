package com.synexoit.weatherapplication.presentation.viewmodel.search

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.exceptions.Failure
import com.synexoit.weatherapplication.presentation.data.entity.CityPlace
import com.synexoit.weatherapplication.presentation.data.entity.CityPreview
import com.synexoit.weatherapplication.presentation.usecase.*
import com.synexoit.weatherapplication.presentation.util.ListStatus
import com.synexoit.weatherapplication.presentation.util.ListWrapper
import com.synexoit.weatherapplication.presentation.util.Resource
import com.synexoit.weatherapplication.presentation.util.Status
import com.synexoit.weatherapplication.presentation.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase,
                                          private val cityPreviewUseCase: CityPreviewUseCase,
                                          private val cityUseCaseImpl: CityUseCase,
                                          private val geocodeUseCase: GeocodeUseCase,
                                          private val locationUseCase: LocationUseCase) : BaseViewModel() {

    companion object {
        const val GO_TO_MAIN_ACTIVITY = 1000
    }

    val cityList = MutableLiveData<ListWrapper<CityPreview>>()
    val isButtonVisible = MutableLiveData<Boolean>()
    val isLocationEnabled = MutableLiveData<Boolean>()

    init {
        getCityListFromDatabase()
    }

    private fun processResponse(response: Resource<City>, lastItemPosition: Int) {
        when (response.status) {
            is Status.Success -> {
                cityList.value?.let { wrapper ->
                    response.data?.run { wrapper.list.add(CityPreview(name, address, placeId, lastItemPosition)) }
                    setCityPreviewListValue(ListWrapper(ListStatus.Refresh(), wrapper.list))
                }
            }

            is Status.Error -> handleFailure(Failure.UnknownAppError(response.message))
        }
    }

    fun getLocationStatus() {
        addDisposable(locationUseCase.isLocationEnabled()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { handleFailure(it) }
                .subscribe { isEnabled -> isLocationEnabled.value = isEnabled })
    }

    fun getCurrentLocation(lastItemPosition: Int) {
        addDisposable(locationUseCase.getUserLocation()
                .flatMap { geocodeUseCase.getGeocodeCityData(it.lat, it.lng) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { handleFailure(it) }
                .subscribe { cityPlace -> getCity(cityPlace, lastItemPosition) })
    }

    fun getCity(cityPlace: CityPlace, lastItemPosition: Int) {
        cityList.value?.run {
            val city = list.singleOrNull { it.placeId == cityPlace.id }
            city?.run {
                handleFailure(Throwable("City already in database"))
                return@getCity
            }
        }

        addDisposable(weatherUseCase.getCity(cityPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { handleFailure(it) }
                .subscribe { processResponse(it, lastItemPosition) })
    }

    private fun getCityListFromDatabase() {
        addDisposable(cityPreviewUseCase.getCityPreviewList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { handleFailure(it) }
                .subscribe { setCityPreviewListValue(ListWrapper(ListStatus.New(), it.toMutableList())) })
    }

    fun deleteCity(city: CityPreview) {
        addDisposable(cityPreviewUseCase.deleteCity(city.placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { handleFailure(it) }
                .subscribe { _ ->
                    cityList.value?.run {
                        list.remove(city)
                        setCityPreviewListValue(ListWrapper(ListStatus.Refresh(), list))
                    }
                })
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

            addDisposable(cityUseCaseImpl.swapPositionsAndUpdateDatabase(pairList)
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