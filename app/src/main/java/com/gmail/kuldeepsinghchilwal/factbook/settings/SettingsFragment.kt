package com.gmail.kuldeepsinghchilwal.factbook.settings


import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.gmail.kuldeepsinghchilwal.factbook.R
import com.gmail.kuldeepsinghchilwal.factbook.viewmodels.HomeFragmentViewModel

class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var viewModel: HomeFragmentViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        val dailyNotification: SwitchPreferenceCompat? = findPreference("daily_notification")

        //setting click listener on daily_notification preference and defining actions accordingly
        dailyNotification?.setOnPreferenceClickListener {
            if(dailyNotification.isChecked) {
                viewModel.alarmSet( true)
            }
            else{
                viewModel.alarmSet(false)
            }
            true
        }
    }
}