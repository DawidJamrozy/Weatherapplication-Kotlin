package com.synexoit.weatherapplication.ui.base.adapter

import com.synexoit.weatherapplication.presentation.data.entity.CityPreview
import com.synexoit.weatherapplication.presentation.data.entity.DayData
import com.synexoit.weatherapplication.presentation.data.entity.DayDetails
import com.synexoit.weatherapplication.presentation.data.entity.Progress
import com.synexoit.weatherapplication.presentation.util.ViewType
import com.synexoit.weatherapplication.ui.city.DayDelegateAdapter
import com.synexoit.weatherapplication.ui.city.DayDetailsDelegateAdapter
import com.synexoit.weatherapplication.ui.search.adapter.CityPreviewDelegateAdapter
import com.synexoit.weatherapplication.ui.search.adapter.ProgressDelegateAdapter
import com.synexoit.weatherapplication.util.ViewTypeDelegateInterface

open class UniversalAdapter(list: MutableList<ViewType> = mutableListOf()) : BaseRecyclerAdapter<ViewType>(list) {

    companion object {
        const val VIEW_CITY_PREVIEW = CityPreview.VIEW_TYPE
        const val VIEW_PROGRESS = Progress.VIEW_TYPE
        const val VIEW_DAY = DayData.VIEW_TYPE
        const val VIEW_DAY_DETAILS = DayDetails.VIEW_TYPE
    }

    init {
        mDelegateAdapters.run {
            put(BindingLayoutHelper.getLayoutResource(VIEW_CITY_PREVIEW), CityPreviewDelegateAdapter(list))
            put(BindingLayoutHelper.getLayoutResource(VIEW_PROGRESS), ProgressDelegateAdapter())
            put(BindingLayoutHelper.getLayoutResource(VIEW_DAY), DayDelegateAdapter(list))
            put(BindingLayoutHelper.getLayoutResource(VIEW_DAY_DETAILS), DayDetailsDelegateAdapter(list))
        }
    }

    fun getDelegateAdapter(viewResId: Int): ViewTypeDelegateInterface =
            mDelegateAdapters[BindingLayoutHelper.getLayoutResource(viewResId)]

}