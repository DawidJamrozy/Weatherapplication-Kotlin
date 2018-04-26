package com.synexoit.weatherapp.ui.search

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.data.model.CityPlace
import com.synexoit.weatherapp.databinding.ActivitySearchBinding
import com.synexoit.weatherapp.ui.base.BaseActivity
import com.synexoit.weatherapp.ui.base.adapter.UniversalAdapter
import com.synexoit.weatherapp.ui.base.navigator.Navigator
import com.synexoit.weatherapp.util.OnItemClickListener
import com.synexoit.weatherapp.util.getViewModel
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private lateinit var mGeoDataClient: GeoDataClient
    private lateinit var mPlaceDetectionClient: PlaceDetectionClient

    @Inject
    protected lateinit var mNavigator: Navigator
    private lateinit var mViewModel: SearchViewModel
    private val mAdapter = UniversalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel(SearchViewModel::class.java, mViewModelFactory)
        mBinding.vm = mViewModel

        mGeoDataClient = Places.getGeoDataClient(this)
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this)

        fragmentManager?.let {
            val autoComplete = it.findFragmentById(R.id.place_autocomplete) as PlaceAutocompleteFragment
            autoComplete.setOnPlaceSelectedListener(placeListener)
        }

        initRecyclerView()
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
               mAdapter.addNewItem(cityPlace)
            }
            Timber.d("onPlaceSelected(): ${place.toString()}")
        }

        override fun onError(place: Status?) {
            Timber.d("onError(): ${place.toString()}")
        }
    }

    private val mOnClickListener = object : OnItemClickListener {
        override fun onItemClick(position: Int, view: View?) {
            Timber.d("onItemClick(): $position")
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_search

    override fun getScreenTitle(): String = getString(R.string.fragment_search_toolbar_title)

    override fun isDisplayingBackArrow(): Boolean = true


}