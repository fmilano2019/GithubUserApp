package com.example.githubuserapp.util

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.githubuserapp.R
import com.google.android.material.snackbar.Snackbar

fun Context.snackbar(view: View, message: String) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.design_default_color_error))
    snackbar.show()
}