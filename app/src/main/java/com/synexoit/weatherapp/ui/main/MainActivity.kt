package com.synexoit.weatherapp.ui.main

import android.os.Bundle
import com.f2prateek.dart.HensonNavigable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.databinding.ActivityMainBinding
import com.synexoit.weatherapp.ui.base.BaseActivity
import com.synexoit.weatherapp.ui.base.navigator.Navigator
import com.synexoit.weatherapp.util.getViewModel
import javax.inject.Inject

@HensonNavigable
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    protected lateinit var navigator: Navigator
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(MainViewModel::class.java, mViewModelFactory)
        mBinding.vm = viewModel
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun getScreenTitle(): String = ""

}
