package com.zakrodionov.protovary.data.storage

import android.content.Context
import androidx.core.content.edit
import com.zakrodionov.protovary.app.util.ThemeUtils.DEFAULT_MODE
import org.jetbrains.anko.defaultSharedPreferences

class PreferenceStorage(context: Context) {
    companion object {
        const val THEME_KEY = "theme_key"
    }

    private val sharedPreferences by lazy { context.defaultSharedPreferences }

    var theme: String
        get() = sharedPreferences.getString(THEME_KEY, DEFAULT_MODE) ?: DEFAULT_MODE
        set(value) {
            sharedPreferences.edit { putString(THEME_KEY, value) }
        }
}
