package com.synexoit.weatherapplication.ui.base.adapter

import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.presentation.data.entity.CityPreview
import com.synexoit.weatherapplication.presentation.data.entity.DayData
import com.synexoit.weatherapplication.presentation.data.entity.DayDetails
import com.synexoit.weatherapplication.presentation.data.entity.Progress


class BindingLayoutHelper {

    companion object {
        fun getLayoutResource(type: Int): Int {
            return when (type) {
                Progress.VIEW_TYPE -> R.layout.item_progress
                DayDetails.VIEW_TYPE -> R.layout.item_day_details
                DayData.VIEW_TYPE -> R.layout.item_day
                CityPreview.VIEW_TYPE -> R.layout.item_city_preview
                else -> throw UnknownLayoutEntityResource()
            }
        }

        private class UnknownLayoutEntityResource : Throwable()
    }
}