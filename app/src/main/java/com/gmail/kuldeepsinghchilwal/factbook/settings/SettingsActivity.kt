package com.gmail.kuldeepsinghchilwal.factbook.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.gmail.kuldeepsinghchilwal.factbook.R

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener,
Preference.SummaryProvider<ListPreference> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Register the listener in the onCreate(),This will ensure that the changes are
        // made almost instantly on user input
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {

        //Unregister the listener,prevents memory leakage
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val appTheme = "app_theme"
        key?.let {
            if (it == appTheme) sharedPreferences?.let { pref ->
                val selectThemeValues = resources.getStringArray(R.array.Select_theme_style_values)
                when (pref.getString(appTheme, selectThemeValues[0])) {
                    selectThemeValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    selectThemeValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    selectThemeValues[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

    override fun provideSummary(preference: ListPreference?): CharSequence {
        return if (preference?.key == getString(R.string.select_theme_pref)) preference.entry
        else {
            "Unknown Preference"
        }
    }
}



