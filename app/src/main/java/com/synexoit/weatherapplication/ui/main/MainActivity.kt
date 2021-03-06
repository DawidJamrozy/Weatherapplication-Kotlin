package com.synexoit.weatherapplication.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.f2prateek.dart.HensonNavigable
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.data.extensions.failure
import com.synexoit.weatherapplication.data.extensions.getViewModel
import com.synexoit.weatherapplication.data.extensions.observe
import com.synexoit.weatherapplication.databinding.ActivityMainBinding
import com.synexoit.weatherapplication.presentation.viewmodel.main.MainViewModel
import com.synexoit.weatherapplication.ui.base.BaseFragmentActivity
import com.synexoit.weatherapplication.ui.city.CityFragmentBuilder
import com.synexoit.weatherapplication.util.CityPagerAdapter
import timber.log.Timber

@HensonNavigable
class MainActivity : BaseFragmentActivity<ActivityMainBinding>() {

    companion object {
        const val SETTINGS_REQUEST_CODE = 10001
    }

    private lateinit var viewModel: MainViewModel

    override val contentResId: Int
        get() = 0

    override val layoutResId: Int
        get() =  R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(viewModelFactory) {
            observe(cityIdList, ::handleCityIdList)
            observe(onClickEvent, ::handleOnClick)
            failure(failure, ::handleError)
        }

        binding.vm = viewModel

        if(savedInstanceState == null)
            viewModel.loadCityIdListFromDatabase()
    }


    private fun setUpViewPagerAdapter(cityIdList: List<String>) {
        val cityPagerAdapter = CityPagerAdapter(supportFragmentManager)
        cityIdList.onEach { cityPagerAdapter.addFragment(CityFragmentBuilder.newCityFragment(it)) }
        binding.viewPager.adapter = cityPagerAdapter
    }

    private fun handleCityIdList(list: List<String>?) {
        list?.run { if(isEmpty()) finish() else setUpViewPagerAdapter(this) }
    }

    private fun handleError(throwable: Throwable?) {
        throwable?.let { showError(it) }
    }

    private fun handleOnClick(onClickEvent: Int?) {
        onClickEvent?.let {
            when(it) {
                MainViewModel.ADD_NEW_CITY -> goToSearchActivity()
            }
        }
    }

    private fun goToSearchActivity() {
        val intent = navigator.getHensonIntent().gotoSearchActivity().build()
        navigator.startActivityForResult(intent, SETTINGS_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(resultCode) {
            Activity.RESULT_OK -> viewModel.loadCityIdListFromDatabase()
            else  -> Timber.d("onActivityResult(): ignore")
        }
    }
}
