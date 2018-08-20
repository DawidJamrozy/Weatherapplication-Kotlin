package com.synexoit.weatherapplication.ui.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import com.f2prateek.dart.HensonNavigable
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.data.entity.CityPlace
import com.synexoit.weatherapplication.data.entity.CityPreview
import com.synexoit.weatherapplication.data.exceptions.Failure
import com.synexoit.weatherapplication.data.extensions.failure
import com.synexoit.weatherapplication.data.extensions.getViewModel
import com.synexoit.weatherapplication.data.extensions.observe
import com.synexoit.weatherapplication.databinding.ActivitySearchBinding
import com.synexoit.weatherapplication.ui.base.BaseActivity
import com.synexoit.weatherapplication.ui.base.adapter.UniversalAdapter
import com.synexoit.weatherapplication.ui.main.MainActivity
import com.synexoit.weatherapplication.ui.search.adapter.CityPreviewDelegateAdapter
import com.synexoit.weatherapplication.util.ListStatus
import com.synexoit.weatherapplication.util.ListWrapper
import com.synexoit.weatherapplication.util.RecyclerViewTouchHelper
import timber.log.Timber

@HensonNavigable
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_search
    override val screenTitle: String
        get() = getString(R.string.fragment_search_toolbar_title)

    private lateinit var viewModel: SearchViewModel

    private val recyclerAdapter = UniversalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(viewModelFactory) {
            observe(cityList, ::handleCityPreviewList)
            observe(onClickEvent, ::handleOnClick)
            failure(failure, ::handleFailure)
        }
        binding.vm = viewModel

        initAutoComplete()
        initRecyclerView()
    }

    private fun handleCityPreviewList(cityList: ListWrapper<CityPreview>?) {
        cityList?.run {
            when (status) {
                is ListStatus.New -> recyclerAdapter.addNewList(list)
                is ListStatus.Refresh -> {
                    recyclerAdapter.loadWithDifference(list)
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

    private fun handleOnClick(onClickEvent: Int?) {
        onClickEvent?.let {
            when(it) {
                SearchViewModel.GO_TO_MAIN_ACTIVITY -> goToMainActivity()
            }
        }
    }

    private fun initAutoComplete() {
        fragmentManager?.run {
            val autoComplete = findFragmentById(R.id.place_autocomplete) as PlaceAutocompleteFragment
            val filter = AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build()

            autoComplete.setOnPlaceSelectedListener(placeListener)
            autoComplete.setFilter(filter)
        }
    }

    private fun initRecyclerView() {
      with(binding.recyclerView) {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
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
                viewModel.getCity(cityPlace, recyclerAdapter.getListSize())
            }
        }

        override fun onError(place: Status?) {
            Timber.d("onError(): ${place.toString()}")
        }
    }

    private fun goToMainActivity() {
        if (callingActivity == null)
            navigator.startActivity(Intent(this, MainActivity::class.java))
        else
            finish()
    }
}