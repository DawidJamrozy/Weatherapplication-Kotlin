package com.synexoit.weatherapp.ui.city

import android.os.Bundle
import com.synexoit.weatherapp.databinding.FragmentCityBinding
import com.synexoit.weatherapp.ui.base.BaseFragment
import com.synexoit.weatherapp.ui.base.navigator.FragmentNavigator
import com.synexoit.weatherapp.util.getViewModel
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
class CityFragment : BaseFragment<FragmentCityBinding>() {

    @Inject
    protected lateinit var mNavigator: FragmentNavigator
    private lateinit var mViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel(CityViewModel::class.java, mViewModelFactory)
        mBinding.vm = mViewModel
    }

    override fun getScreenTitle(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutResId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}