package com.synexoit.weatherapplication.data.entity

import com.synexoit.weatherapplication.util.ViewType

/**
 * Created by Dawid on 25.12.2017.
 */
class ItemProgress : ViewType {

    private val PROGRESS = "PROGRESS"

    override fun getViewType(): Int = 0

    override fun getUniqueId(): String = PROGRESS
}