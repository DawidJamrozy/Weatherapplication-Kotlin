package com.synexoit.weatherapp.data.entity

import android.os.Parcelable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.ViewType
import kotlinx.android.parcel.Parcelize

/**
 * Created by dawidjamrozy on 09.05.2018.
 */
@Parcelize
data class CityPreview(var name: String = "",
                       var address: String = "",
                       var placeId: String = "") : ViewType, Parcelable {

    override fun getViewType(): Int = R.layout.item_city_preview

    override fun getUniqueId(): String = placeId

}