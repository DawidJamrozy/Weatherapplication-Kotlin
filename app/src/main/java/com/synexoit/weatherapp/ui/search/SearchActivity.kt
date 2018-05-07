package com.synexoit.weatherapp.ui.search

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.data.exceptions.CityAlreadyInDatabaseException
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.databinding.ActivitySearchBinding
import com.synexoit.weatherapp.ui.base.BaseActivity
import com.synexoit.weatherapp.ui.base.adapter.UniversalAdapter
import com.synexoit.weatherapp.ui.base.navigator.Navigator
import com.synexoit.weatherapp.util.OnItemClickListener
import com.synexoit.weatherapp.util.getViewModel
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    @Inject
    protected lateinit var mNavigator: Navigator
    private lateinit var mViewModel: SearchViewModel
    private val mAdapter = UniversalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel(SearchViewModel::class.java, mViewModelFactory)
        mBinding.vm = mViewModel

        initAutoComplete()
        initRecyclerView()
        registerObservers()
    }

    override fun getLayoutResId(): Int = R.layout.activity_search

    override fun getScreenTitle(): String = getString(R.string.fragment_search_toolbar_title)

    override fun isDisplayingBackArrow(): Boolean = true

    private fun registerObservers() {
        mViewModel.getCityPlaceListObserver().observe(this, Observer { list ->
            list?.let {
                mAdapter.refreshWithNewList(it)
            }
        })

        mViewModel.getCityPlaceObserver().observe(this, Observer { cityPlace ->
            cityPlace?.let {
                mAdapter.addNewItem(it)
            }
        })

        mViewModel.getErrorObserver().observe(this, Observer { error ->
            error?.let {
                when (it) {
                    is CityAlreadyInDatabaseException -> showToast(it.uiMessage)
                    else -> showToast(it.message)
                }
            }
        })
    }

    private fun initAutoComplete() {
        fragmentManager?.let {
            val autoComplete = it.findFragmentById(R.id.place_autocomplete) as PlaceAutocompleteFragment
            val filter = AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build()

            autoComplete.setOnPlaceSelectedListener(placeListener)
            autoComplete.setFilter(filter)
        }
    }

    private fun initRecyclerView() {
        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBinding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        mAdapter.setOnItemClickListener(mOnClickListener)
    }

    private val placeListener = object : PlaceSelectionListener {
        override fun onPlaceSelected(place: Place?) {
            place?.let {
                val cityPlace = CityPlace(it.name.toString(), it.address.toString(), it.latLng.latitude, it.latLng.longitude, it.id)
                mViewModel.insertPlaceToDatabase(cityPlace)
            }
            Timber.d("onPlaceSelected(): ${place.toString()}")
        }

        override fun onError(place: Status?) {
            Timber.d("onError(): ${place.toString()}")
        }
    }

    private val mOnClickListener = object : OnItemClickListener {
        override fun onItemClick(position: Int, view: View?) {
            //TODO 26.04.2018 by Dawid Jamroży
            Timber.d("onItemClick(): $position")
        }
    }
}