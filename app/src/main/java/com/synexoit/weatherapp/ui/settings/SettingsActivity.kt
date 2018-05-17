package com.synexoit.weatherapp.ui.settings

import android.preference.PreferenceActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.synexoit.weatherapp.R
import java.util.*

class SettingsActivity : PreferenceActivity() {

    override fun onBuildHeaders(target: MutableList<Header>?) {
        super.onBuildHeaders(target)
        // setContentView in onCreate cause error
        //TODO 17.05.2018 by Dawid Jamroży change language
        setContentView(R.layout.activity_settings)
        setUpCustomToolbar()
        loadHeadersFromResource(R.xml.headers_preference, target)
    }

    override fun isValidFragment(fragmentName: String?): Boolean = SettingsFragment::class.java.name == fragmentName

    private fun setUpCustomToolbar() {
        val arrowBack: ImageView? = findViewById(R.id.toolbar_back_arrow)
        val toolbarTitle: TextView? = findViewById(R.id.toolbar_title)

        arrowBack?.run {
            visibility = View.VISIBLE
            setOnClickListener { onBackPressed() }
        }
        toolbarTitle?.run { text = getScreenTitle() }
    }

    private fun getScreenTitle() = getString(R.string.settings_title)

    @Suppress("DEPRECATION")
    fun setLanguage(language: String) {
        //TODO change to context wrapper
        val conf = resources.configuration
        conf.locale = Locale(language)
        resources.updateConfiguration(conf, resources.displayMetrics)
    }
}