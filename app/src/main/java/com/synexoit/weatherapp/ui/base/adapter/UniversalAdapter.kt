package com.synexoit.weatherapp.ui.base.adapter

import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.ui.search.adapter.PlaceDelegateAdapter
import com.synexoit.weatherapp.util.ViewType
import com.synexoit.weatherapp.util.putNext

open class UniversalAdapter(list: MutableList<ViewType> = mutableListOf()) : BaseRecyclerAdapter<ViewType>(list) {


    private val VIEW_PLACE = R.layout.item_place

    init {
        mDelegateAdapters.putNext(VIEW_PLACE, PlaceDelegateAdapter())
    }
}