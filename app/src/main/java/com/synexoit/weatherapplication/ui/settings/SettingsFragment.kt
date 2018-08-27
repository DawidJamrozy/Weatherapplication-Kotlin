package com.synexoit.weatherapplication.ui.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.EditTextPreference
import android.preference.ListPreference
import android.support.v7.preference.PreferenceFragmentCompat
import com.synexoit.weatherapplication.R

class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {

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
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings_preferences)
    }
}