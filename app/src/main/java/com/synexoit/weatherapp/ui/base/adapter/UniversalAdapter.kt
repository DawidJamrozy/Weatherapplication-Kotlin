package com.synexoit.weatherapp.ui.base.adapter

import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.ui.search.adapter.CityDelegateAdapter
import com.synexoit.weatherapp.ui.search.adapter.ProgressDelegateAdapter
import com.synexoit.weatherapp.util.ViewType
import com.synexoit.weatherapp.util.putNext

open class UniversalAdapter(list: MutableList<ViewType> = mutableListOf()) : BaseRecyclerAdapter<ViewType>(list) {

    private val VIEW_CITY = R.layout.item_city
    private val VIEW_PROGRESS = R.layout.item_progress

    init {
        mDelegateAdapters.putNext(VIEW_CITY, CityDelegateAdapter())
                .putNext(VIEW_PROGRESS, ProgressDelegateAdapter())
    }
}