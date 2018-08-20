package com.synexoit.weatherapplication.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.f2prateek.dart.HensonNavigable
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.data.extensions.getViewModel
import com.synexoit.weatherapplication.data.extensions.observe
import com.synexoit.weatherapplication.databinding.ActivityMainBinding
import com.synexoit.weatherapplication.ui.base.BaseFragmentActivity
import com.synexoit.weatherapplication.ui.base.navigator.Navigator
import com.synexoit.weatherapplication.ui.city.CityFragmentBuilder
import com.synexoit.weatherapplication.ui.main.MainViewModel
import com.synexoit.weatherapplication.util.ViewPagerAdapter
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
        cityIdList?.let {list ->
            if(list.isEmpty())
                finish()
            else
                setUpViewPagerAdapter(list)
        }
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
            Activity.RESULT_CANCELED -> Timber.d("onActivityResult(): ignore")
        }
    }
}