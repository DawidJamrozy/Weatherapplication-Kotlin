package com.synexoit.weatherapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.CityPreview
import com.synexoit.weatherapp.data.exceptions.Failure
import com.synexoit.weatherapp.data.extensions.failure
import com.synexoit.weatherapp.data.extensions.getViewModel
import com.synexoit.weatherapp.data.extensions.observe
import com.synexoit.weatherapp.databinding.ActivitySearchBinding
import com.synexoit.weatherapp.ui.base.BaseActivity
import com.synexoit.weatherapp.ui.base.adapter.UniversalAdapter
import com.synexoit.weatherapp.ui.base.navigator.Navigator
import com.synexoit.weatherapp.ui.main.MainActivity
import com.synexoit.weatherapp.ui.search.adapter.CityPreviewDelegateAdapter
import com.synexoit.weatherapp.util.ListStatus
import com.synexoit.weatherapp.util.ListWrapper
import com.synexoit.weatherapp.util.RecyclerViewTouchHelper
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    @Inject
    protected lateinit var navigator: Navigator

    private lateinit var viewModel: SearchViewModel

    private val recyclerAdapter = UniversalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(viewModelFactory, {
            observe(cityList, ::handleCityPreviewList)
            observe(event, ::handleEvent)
            failure(failure, ::handleFailure)
        })
        binding.vm = viewModel

        initAutoComplete()
        initRecyclerView()
    }

    override fun getLayoutResId(): Int = R.layout.activity_search

    override fun getScreenTitle(): String = getString(R.string.fragment_search_toolbar_title)

    override fun isDisplayingBackArrow(): Boolean = true

    private fun handleCityPreviewList(cityList: ListWrapper<CityPreview>?) {
        cityList?.let {
            when (it.status) {
                is ListStatus.New -> recyclerAdapter.addNewList(it.list)
                is ListStatus.Refresh -> {
                    recyclerAdapter.loadWithDifference(it.list)
                    setResult(RESULT_OK)
                }
            }
        }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let {
            when (it) {
                is Failure.CityAlreadyInDatabaseException -> showToast(stringId = R.string.error_city_already_in_database)
                is UnknownError -> showToast(it.message)
                else -> showToast()
            }
        }
        recyclerAdapter.hideProgress()
    }

    private fun handleEvent(event: Int?) {
        if (callingActivity == null)
            navigator.startActivity(Intent(MainActivity@ this, MainActivity::class.java))
        else
            finish()
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
        binding.recyclerView.run {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
            val touchCallback = RecyclerViewTouchHelper(recyclerAdapter.getDelegateAdapter(UniversalAdapter.VIEW_CITY_PREVIEW) as CityPreviewDelegateAdapter)
            val touchHelper = ItemTouchHelper(touchCallback)
            touchHelper.attachToRecyclerView(this)
        }

        recyclerAdapter.setViewModel(viewModel)
    }

    private val placeListener = object : PlaceSelectionListener {
        override fun onPlaceSelected(place: Place?) {
            place?.let {
                recyclerAdapter.showProgressAtLastPosition()
                val cityPlace = CityPlace(it.name.toString(), it.address.toString(), it.latLng.latitude, it.latLng.longitude, it.id)
                viewModel.getCity(cityPlace)
            }
        }

        override fun onError(place: Status?) {
            Timber.d("onError(): ${place.toString()}")
        }
    }
}