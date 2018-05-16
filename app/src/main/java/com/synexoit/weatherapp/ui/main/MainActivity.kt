package com.synexoit.weatherapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.f2prateek.dart.HensonNavigable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.data.extensions.getViewModel
import com.synexoit.weatherapp.data.extensions.observe
import com.synexoit.weatherapp.databinding.ActivityMainBinding
import com.synexoit.weatherapp.ui.base.BaseFragmentActivity
import com.synexoit.weatherapp.ui.base.navigator.Navigator
import com.synexoit.weatherapp.ui.city.CityFragmentBuilder
import com.synexoit.weatherapp.util.ViewPagerAdapter
import timber.log.Timber
import javax.inject.Inject

@HensonNavigable
class MainActivity : BaseFragmentActivity<ActivityMainBinding>() {

    companion object {
        const val SETTINGS_REQUEST_CODE = 10001
    }

    @Inject
    protected lateinit var navigator: Navigator

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(viewModelFactory, {
            observe(cityIdList, ::handleCityIdList)
            observe(onClickEvent, ::handleOnClick)
        })
        binding.vm = viewModel
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun getContentResId(): Int  = 0

    private fun setUpViewPagerAdapter(cityIdList: List<String>) {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        cityIdList.onEach { viewPagerAdapter.addFragment(CityFragmentBuilder.newCityFragment(it)) }
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun handleCityIdList(cityIdList: List<String>?) {
        cityIdList?.let { setUpViewPagerAdapter(it) }
    }

    private fun handleOnClick(onClickEvent: Int?) {
        onClickEvent?.let {
            when(onClickEvent) {
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
            Activity.RESULT_CANCELED -> Timber.d("onActivityResult(): ignore")
        }
    }
}
