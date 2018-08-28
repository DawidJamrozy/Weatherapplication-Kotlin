package com.synexoit.weatherapplication.presentation.data.entity

import com.synexoit.weatherapplication.presentation.data.util.ViewType

/**
 * Created by dawidjamrozy on 09.05.2018.
 */
data class CityPreview(var name: String = "",
                       var address: String = "",
                       var placeId: String = "",
                       var sortPosition: Int = 0) : ViewType {

    companion object {
        const val VIEW_TYPE = 3
    }

    override val viewType: Int
        get() = VIEW_TYPE

    override val uniqueId: String
        get() = placeId
}