package com.synexoit.weatherapp.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.f2prateek.dart.HensonNavigable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.databinding.ActivityMainBinding
import com.synexoit.weatherapp.ui.base.BaseFragmentActivity
import com.synexoit.weatherapp.ui.base.navigator.Navigator
import com.synexoit.weatherapp.ui.city.CityFragmentBuilder
import com.synexoit.weatherapp.util.ViewPagerAdapter
import com.synexoit.weatherapp.util.getViewModel
import javax.inject.Inject

@HensonNavigable
class MainActivity : BaseFragmentActivity<ActivityMainBinding>() {

    @Inject
    protected lateinit var navigator: Navigator

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(MainViewModel::class.java, mViewModelFactory)
        binding.vm = viewModel
        registerObservers()
    }

    private fun setUpViewPagerAdapter(cityIdList: List<String>) {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        cityIdList.onEach { viewPagerAdapter.addFragment(CityFragmentBuilder.newCityFragment(it)) }
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun registerObservers() {
        viewModel.getCityIdList().observe(this, Observer { cityIdList ->
            cityIdList?.let { setUpViewPagerAdapter(it) }
        })
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun getScreenTitle(): String = ""
    override fun getContentResId(): Int {
        return 0
    }
}
