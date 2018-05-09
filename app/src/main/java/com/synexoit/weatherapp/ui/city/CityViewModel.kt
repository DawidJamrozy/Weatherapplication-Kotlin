package com.synexoit.weatherapp.ui.city

import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
class CityViewModel @Inject constructor() : BaseViewModel() {

    private var city: City? = null

    fun setCity(city: City) {
        this.city = city
    }

    fun getCity() = city

}