package com.synexoit.weatherapp.util

import android.support.v7.util.DiffUtil

/**
 * Created by dawidjamrozy on 09.05.2018.
 */
class CityDiffCallback(private val newItems: List<ViewType>,
                          private val oldItems: List<ViewType>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldItems[oldItemPosition].getUniqueId() == newItems[newItemPosition].getUniqueId()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldItems[oldItemPosition] == newItems[newItemPosition]
}