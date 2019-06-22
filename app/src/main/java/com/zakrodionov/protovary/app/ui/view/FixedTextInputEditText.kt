package com.zakrodionov.protovary.app.ui.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class FixedTextInputEditText : TextInputEditText {

    private val superHintHack: CharSequence
        @Throws(NoSuchFieldException::class, IllegalAccessException::class)
        get() {
            val hintField = TextView::class.java.getDeclaredField("mHint")
            hintField.isAccessible = true
            return hintField.get(this) as CharSequence
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getHint(): CharSequence {
        val manufacturer = Build.MANUFACTURER.toUpperCase()
        return if (!manufacturer.contains("MEIZU") || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            super.getHint()
        } else {
            try {
                superHintHack
            } catch (e: Exception) {
                super.getHint()
            }

        }
    }
}