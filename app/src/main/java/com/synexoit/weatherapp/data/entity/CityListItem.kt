package com.synexoit.weatherapp.data.entity

import kotlinx.android.parcel.Parcelize

/**
 * Created by dawidjamrozy on 09.05.2018.
 */
@Parcelize
data class CityListItem(var name: String = "",
                        var placeId: String = "",
                        var address: String = "",
                        var id:Long = 0)