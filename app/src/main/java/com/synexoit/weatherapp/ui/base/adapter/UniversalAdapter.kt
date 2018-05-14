package com.synexoit.weatherapp.ui.base.adapter

import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.ui.city.DayDelegateAdapter
import com.synexoit.weatherapp.ui.city.DayDetailsDelegateAdapter
import com.synexoit.weatherapp.ui.search.adapter.CityPreviewDelegateAdapter
import com.synexoit.weatherapp.ui.search.adapter.ProgressDelegateAdapter
import com.synexoit.weatherapp.util.ViewType

open class UniversalAdapter(list: MutableList<ViewType> = mutableListOf()) : BaseRecyclerAdapter<ViewType>(list) {

    companion object {
        const val VIEW_CITY_PREVIEW = R.layout.item_city_preview
        const val VIEW_PROGRESS = R.layout.item_progress
        const val VIEW_DAY = R.layout.item_day
        const val VIEW_DAY_DETAILS = R.layout.item_day_details
    }

    init {
        mDelegateAdapters.run {
            put(VIEW_CITY_PREVIEW, CityPreviewDelegateAdapter(list))
            put(VIEW_PROGRESS, ProgressDelegateAdapter())
            put(VIEW_DAY, DayDelegateAdapter(list))
            put(VIEW_DAY_DETAILS, DayDetailsDelegateAdapter(list))
        }
    }

    fun getDelegateAdapter(viewResId: Int) = mDelegateAdapters[viewResId]

}