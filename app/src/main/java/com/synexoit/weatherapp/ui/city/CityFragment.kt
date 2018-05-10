package com.synexoit.weatherapp.ui.city

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.databinding.FragmentCityBinding
import com.synexoit.weatherapp.ui.base.BaseFragment
import com.synexoit.weatherapp.ui.base.navigator.FragmentNavigator
import com.synexoit.weatherapp.util.getViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
@FragmentWithArgs
class CityFragment : BaseFragment<FragmentCityBinding>() {

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
        binding.id.text = id

        Timber.d("onViewCreated(): ${SystemClock.currentThreadTimeMillis()}")
        viewModel.loadCityFromDatabase(id)
        registerObservers()
    }

    private fun registerObservers() {
        viewModel.getCityObserver().observe(this, Observer {
            it?.let {
                Timber.d("registerObservers(): $it")
                Timber.d("registerObservers(): ${SystemClock.currentThreadTimeMillis()}")
            }
        })
    }

    override fun getScreenTitle(): String = ""

    override fun getLayoutResId(): Int = R.layout.fragment_city
}