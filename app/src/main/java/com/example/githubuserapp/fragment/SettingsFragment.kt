package com.example.githubuserapp.fragment

import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.TimePicker
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.example.githubuserapp.R
import com.example.githubuserapp.background.AlarmReceiver
import com.example.githubuserapp.preference.SharedPrefManager
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
    TimePickerDialog.OnTimeSetListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPreference: SharedPreferences
    private var reminderSwitch: SwitchPreferenceCompat? = null
    private var reminderTimePreference: Preference? = null
    private var reminderLanguagePreference: Preference? = null
    private lateinit var simpleDateFormat: SimpleDateFormat
    private var hours = 0
    private var minutes = 0

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preference, rootKey)
        init()
        setSummaries()
        setReminderSwitch()
    }

    private fun setReminderSwitch() {
        when (sharedPreference.getBoolean("reminder_switch", false)) {
            true -> reminderTimePreference?.isVisible = true
            false -> reminderTimePreference?.isVisible = false
        }
    }

    private fun init() {
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        reminderSwitch = findPreference("reminder_switch")
        reminderTimePreference = findPreference("reminder_time")
        reminderLanguagePreference = findPreference("language")
        reminderTimePreference?.onPreferenceClickListener = this
        reminderLanguagePreference?.onPreferenceClickListener = this
        simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference?.key) {
            "reminder_time" -> {
                hours = SharedPrefManager(requireContext()).getHour()
                minutes = SharedPrefManager(requireContext()).getMinute()
                TimePickerDialog(context, this, hours, minutes, true).show()
            }
            "language" -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        SharedPrefManager(requireContext()).setHour(hourOfDay)
        SharedPrefManager(requireContext()).setMinute(minute)
        setSummaries()

        AlarmReceiver().setReminder(requireContext())
    }

    private fun setSummaries() {
        reminderTimePreference?.summary = simpleDateFormat.format(
            SharedPrefManager(requireContext()).getHourAndMinute()
        )
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "reminder_switch" -> {
                when (sharedPreferences?.getBoolean("reminder_switch", false)) {
                    true -> {
                        reminderTimePreference?.isVisible = true
                        AlarmReceiver().setReminder(requireContext())
                    }
                    false -> {
                        reminderTimePreference?.isVisible = false
                        AlarmReceiver().cancelReminder(requireContext())
                    }
                }
            }
        }
    }

}