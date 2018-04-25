package com.synexoit.weatherapp.ui.main

import android.os.Bundle
import com.f2prateek.dart.HensonNavigable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.databinding.ActivityMainBinding
import com.synexoit.weatherapp.ui.base.BaseActivity

@HensonNavigable
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun getScreenTitle(): String = ""
}
