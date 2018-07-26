package com.synexoit.weatherapplication.data.entity

import android.os.Parcelable
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.util.ViewType
import kotlinx.android.parcel.Parcelize

/**
 * Created by dawidjamrozy on 09.05.2018.
 */
@Parcelize
data class CityPreview(var name: String = "",
                       var address: String = "",
                       var placeId: String = "",
                       var sortPosition: Int = 0) : ViewType, Parcelable {

    override fun getViewType(): Int = R.layout.item_city_preview

    override fun getUniqueId(): String = placeId

}