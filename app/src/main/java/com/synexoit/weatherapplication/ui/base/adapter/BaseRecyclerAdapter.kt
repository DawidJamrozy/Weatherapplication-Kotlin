package com.synexoit.weatherapplication.ui.base.adapter

import android.arch.lifecycle.ViewModel
import android.support.v4.util.SparseArrayCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.synexoit.weatherapplication.presentation.data.entity.Progress
import com.synexoit.weatherapplication.presentation.data.util.ViewType
import com.synexoit.weatherapplication.util.ItemDiffCallback
import com.synexoit.weatherapplication.util.ViewTypeDelegateInterface

abstract class BaseRecyclerAdapter<in T : ViewType>(private val mList: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Suppress("UNCHECKED_CAST")
    private val progress = Progress() as T

    protected var mDelegateAdapters = SparseArrayCompat<ViewTypeDelegateInterface>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return mDelegateAdapters[viewType].onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mDelegateAdapters[getItemViewType(position)].onBindViewHolder(holder, mViewModel)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return BindingLayoutHelper.getLayoutResource(mList[position].viewType)
    }

    private var mViewModel: ViewModel? = null

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

        val indexToInsert = if (mList.isEmpty()) 0 else (mList.size - 1)
        mList.addAll(indexToInsert, list)
        notifyDataSetChanged()
    }

    fun loadWithDifference(list: MutableList<out T>) {
        val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(list, mList))
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
        if (isProgressVisible()) return

        mList.add(position, progress)
        notifyItemInserted(position)
    }

    fun showProgressAtLastPosition() {
        showProgressAtPosition(mList.size)
    }

    fun showProgress() {
     if (isProgressVisible()) return

        mList.clear()
        mList.add(progress)
        notifyDataSetChanged()
    }

    fun getListSize() = mList.size

    fun hideProgress() {
      if (!isProgressVisible()) return

        val index = mList.indexOfFirst { it is Progress }

        if (index != -1) {
            mList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun isProgressVisible() = mList.contains(progress)
}