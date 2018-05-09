package com.synexoit.weatherapp.ui.base.adapter

import android.arch.lifecycle.ViewModel
import android.support.v4.util.SparseArrayCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.synexoit.weatherapp.data.entity.ItemProgress
import com.synexoit.weatherapp.util.*

abstract class BaseRecyclerAdapter<T : ViewType>(val mList: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Suppress("UNCHECKED_CAST")
    private val progress = ItemProgress() as T

    protected var mDelegateAdapters = SparseArrayCompat<ViewTypeDelegateInterface>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return mDelegateAdapters[viewType].onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mDelegateAdapters[getItemViewType(position)].onBindViewHolder(holder, mList, mOnItemClickListener, mViewModel)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return mList[position].getViewType()
    }

    private var mOnItemClickListener: OnItemClickListener? = null
    private var mViewModel: ViewModel? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    fun setViewModel(viewModel: ViewModel) {
        this.mViewModel = viewModel
    }

    fun addNewList(list: MutableList<out T>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun refreshWithNewItem(item: T) {
        mList.clear()
        mList.add(item)
        notifyDataSetChanged()
    }

    fun addNewItemsToList(list: MutableList<out T>, hideProgress: Boolean = false) {
        if (hideProgress)
            mList.clear()

        val indexToInsert = if (mList.isEmpty()) 0 else mList.realSize()
        mList.addAll(indexToInsert, list)
        notifyDataSetChanged()
    }

    fun loadWithDifference(list: MutableList<out T>) {
        val diffResult = DiffUtil.calculateDiff(CityDiffCallback(list, mList))
        mList.clear()
        mList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addNewItem(item: T) {
        mList.add(item)
        val index = mList.indexOf(item)
        notifyItemInserted(index)
    }

    fun showProgressAtPosition(position: Int) {
        if (mList.contains(progress)) return

        mList.add(position, progress)
        notifyItemInserted(position)
    }

    fun showProgressAtLastPosition() {
        showProgressAtPosition(mList.size)
    }

    fun showProgress() {
        if (mList.contains(progress)) return

        mList.clear()
        mList.add(progress)
        notifyDataSetChanged()
    }

    fun hideProgress() {
        if (!mList.contains(progress)) return

        val index = mList.indexOfFirst { it is ItemProgress }

        if (index != -1) {
            mList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }
}