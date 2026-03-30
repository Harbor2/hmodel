package com.habit.app.helper

import android.content.Context
import androidx.core.content.edit

class GrantedHelperSP(private val context: Context) {
    companion object {
        private const val PREF_NAME = "app_granted_helper"
        private const val KEY_HAS_GRANTED = "HAS_GRANTED"

        fun checkGranted(context: Context): Boolean {
            val sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sp.getBoolean(KEY_HAS_GRANTED, false)
        }

        fun reportGranted(context: Context) {
            val sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            sp.edit { putBoolean(KEY_HAS_GRANTED, true) }
        }
    }
}