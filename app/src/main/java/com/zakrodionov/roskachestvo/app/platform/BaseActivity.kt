package com.zakrodionov.roskachestvo.app.platform

import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    abstract fun fragmentContainer(): View
}
