package com.synexoit.weatherapp.ui.city

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.databinding.FragmentCityBinding
import com.synexoit.weatherapp.ui.base.BaseFragment
import com.synexoit.weatherapp.ui.base.navigator.FragmentNavigator
import com.synexoit.weatherapp.util.getViewModel
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
@FragmentWithArgs
class CityFragment : BaseFragment<FragmentCityBinding>(), SwipeRefreshLayout.OnRefreshListener {

    @Arg(required = true)
    lateinit var id: String

    @Inject
    protected lateinit var navigator: FragmentNavigator
    private lateinit var viewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(CityViewModel::class.java, mViewModelFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        viewModel.loadCityFromDatabase(id)
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        registerObservers()
    }

    override fun getScreenTitle(): String = ""

    override fun getLayoutResId(): Int = R.layout.fragment_city

    override fun onRefresh() {
        setSwipeRefreshIndicator(true)
        viewModel.refreshWeatherData()
    }

    private fun registerObservers() {
        viewModel.getCity().observe(this, Observer {
            it?.let { setSwipeRefreshIndicator(false) }
        })

        viewModel.getErrorObserver().observe(this, Observer {
            it?.let {
                setSwipeRefreshIndicator(false)
                showToast(it.message)
            }
        })
    }

    private fun setSwipeRefreshIndicator(isRefreshing: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isRefreshing
    }
}