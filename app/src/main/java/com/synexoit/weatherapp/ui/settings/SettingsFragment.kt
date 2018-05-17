package com.synexoit.weatherapp.ui.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.EditTextPreference
import android.preference.ListPreference
import android.preference.PreferenceFragment
import com.synexoit.weatherapp.R

class SettingsFragment : PreferenceFragment(), OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings_preferences)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val preference = findPreference(key)
        val sharedPreferenceEditor = sharedPreferences.edit()

        when(preference) {
            is EditTextPreference -> sharedPreferenceEditor.putString(key, preference.text)
            is CheckBoxPreference -> sharedPreferenceEditor.putBoolean(key, preference.isChecked)
            is ListPreference -> sharedPreferenceEditor.putString(key, preference.value)
        }

        sharedPreferenceEditor.apply()

        if(key == "language") {
            (activity as SettingsActivity).setLanguage((preference as EditTextPreference).text)
            activity.recreate()
        }
    }
}