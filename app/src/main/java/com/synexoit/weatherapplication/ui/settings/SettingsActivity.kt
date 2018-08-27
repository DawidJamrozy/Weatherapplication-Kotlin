package com.synexoit.weatherapplication.ui.settings

import android.os.Bundle
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.databinding.ActivitySettingsBinding
import com.synexoit.weatherapplication.ui.base.BaseFragmentActivity

class SettingsActivity : BaseFragmentActivity<ActivitySettingsBinding>() {

    override val contentResId: Int
        get() = R.id.fragment_container
    override val layoutResId: Int
        get() = R.layout.activity_settings
    override val screenTitle: String
        get() = getString(R.string.settings_title)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null) navigator.replaceFragment(SettingsFragment())
    }
}