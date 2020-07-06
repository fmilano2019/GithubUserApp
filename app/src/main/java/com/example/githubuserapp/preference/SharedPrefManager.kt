package com.example.githubuserapp.preference

import android.content.Context
import androidx.core.content.edit
import java.util.*

class SharedPrefManager(context: Context) {

    companion object {
        const val PREF_NAME = "pref_name"
        const val PREF_HOUR = "pref_hour"
        const val PREF_MINUTE = "pref_minute"
    }

    private var preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setHour(hour: Int) {
        preferences.edit {
            putInt(PREF_HOUR, hour)
        }
    }

    fun getHour(): Int {
        return preferences.getInt(PREF_HOUR, 9)
    }

    fun setMinute(minute: Int) {
        preferences.edit {
            putInt(PREF_MINUTE, minute)
        }
    }

    fun getMinute(): Int {
        return preferences.getInt(PREF_MINUTE, 0)
    }

    fun getHourAndMinute(): Date {
        val cal = Calendar.getInstance().apply {
            set(
                0, 0, 0,
                preferences.getInt(PREF_HOUR, 9),
                preferences.getInt(PREF_MINUTE, 0)
            )
        }
        return cal.time
    }
}