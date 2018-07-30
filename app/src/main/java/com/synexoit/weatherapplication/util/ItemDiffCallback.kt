package com.synexoit.weatherapplication.util

import android.support.v7.util.DiffUtil

/**
 * Created by dawidjamrozy on 09.05.2018.
 */
class ItemDiffCallback(private val newItems: List<ViewType>,
                       private val oldItems: List<ViewType>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldItems[oldItemPosition].uniqueId == newItems[newItemPosition].uniqueId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldItems[oldItemPosition] == newItems[newItemPosition]
}