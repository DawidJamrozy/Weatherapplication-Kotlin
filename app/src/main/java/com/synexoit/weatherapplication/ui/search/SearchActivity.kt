package com.synexoit.weatherapplication.ui.search

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
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
import com.synexoit.weatherapplication.data.entity.CurrentLocation
import com.synexoit.weatherapplication.data.exceptions.Failure
import com.synexoit.weatherapplication.data.extensions.*
import com.synexoit.weatherapplication.data.repository.LocationListener
import com.synexoit.weatherapplication.data.repository.LocationRepository
import com.synexoit.weatherapplication.databinding.ActivitySearchBinding
import com.synexoit.weatherapplication.presentation.data.entity.CityPreview
import com.synexoit.weatherapplication.ui.base.BaseActivity
import com.synexoit.weatherapplication.ui.base.adapter.UniversalAdapter
import com.synexoit.weatherapplication.ui.main.MainActivity
import com.synexoit.weatherapplication.ui.search.adapter.CityPreviewDelegateAdapter
import com.synexoit.weatherapplication.util.ListStatus
import com.synexoit.weatherapplication.util.ListWrapper
import com.synexoit.weatherapplication.util.RecyclerViewTouchHelper
import kotlinx.android.synthetic.main.basic_custom_toolbar.*
import javax.inject.Inject


@HensonNavigable
class SearchActivity : BaseActivity<ActivitySearchBinding>(), LocationListener {

    companion object {
        private const val LOCATION_PERMISSIONS_REQUEST_CODE = 1000
    }

    override val layoutResId: Int
        get() = R.layout.activity_search
    override val screenTitle: String
        get() = getString(R.string.fragment_search_toolbar_title)

    @Inject
    protected lateinit var locationProvider: LocationRepository

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
        initLocationButton()
    }

    private fun initLocationButton() {
        extra_button.run {
            visible()
            background = ContextCompat.getDrawable(context, R.drawable.ic_location_ripple)
            onClick { getUserLocation() }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getUserLocation()
        }
    }

    private fun getUserLocation() {
        if (isLocationPermissionGranted()) {
            if (locationProvider.isLocationEnabled()) {
                startLocatingUser()
            } else {
                showLocalizationOffDialog()
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSIONS_REQUEST_CODE)
        }
    }

    private fun startLocatingUser() {
        if (!recyclerAdapter.isProgressVisible()) {
            recyclerAdapter.showProgressAtLastPosition()
            locationProvider.getUserLocation()
        } else {
            showToast(getString(R.string.operation_in_progress))
        }
    }

    private fun showLocalizationOffDialog() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.localization_dialog_title))
                .setMessage(getString(R.string.localization_dialog_message))
                .setPositiveButton(getString(R.string.common_yes)) { _, _ ->
                    navigator.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }.setNegativeButton(getString(R.string.common_no)) { _, _ -> }
                .create()
                .show()
    }

    override fun onLocationUpdate(currentLocation: CurrentLocation) {
        viewModel.getGeocodeCity(currentLocation.lat, currentLocation.lng, recyclerAdapter.getListSize())
    }

    override fun onLocationError(error: Throwable) {
        showToast(error.message)
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
            when (it) {
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

            autoComplete.run {
                setOnPlaceSelectedListener(placeListener)
                setFilter(filter)
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            val delegateAdapter = recyclerAdapter.getDelegateAdapter(
                    UniversalAdapter.VIEW_CITY_PREVIEW) as CityPreviewDelegateAdapter
            val touchCallback = RecyclerViewTouchHelper(delegateAdapter)
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
            showToast(getString(R.string.place_listener_error) + ":${place.toString()}")
        }
    }

    private fun goToMainActivity() {
        if (callingActivity == null)
            navigator.startActivity(Intent(this, MainActivity::class.java))
        else
            finish()
    }

    /**
     * Returns true if ic_location permissions are granted
     *
     * @return [Boolean]
     */
    private fun isLocationPermissionGranted(): Boolean =
            ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}