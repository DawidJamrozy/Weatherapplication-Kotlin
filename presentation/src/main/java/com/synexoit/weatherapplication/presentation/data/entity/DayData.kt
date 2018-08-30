package com.synexoit.weatherapplication.presentation.data.entity

import com.synexoit.weatherapplication.presentation.util.ViewType

data class DayData(var tempMin: Int,
                   var tempMax: Int,
                   var icon: String,
                   var dayName: String) : ViewType {

    companion object {
        const val VIEW_TYPE = 2
    }

    override val viewType: Int
        get() = VIEW_TYPE

    //TODO 28.08.2018 by Dawid Jamroży String.empty() here
    override val uniqueId: String
        get() = ""

}