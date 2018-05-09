package com.synexoit.weatherapp.data.entity

import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.ViewType

/**
 * Created by Dawid on 25.12.2017.
 */
class ItemProgress : ViewType {

    private val PROGRESS = "PROGRESS"

    override fun getViewType(): Int = R.layout.item_progress

    override fun getUniqueId(): String = PROGRESS
}