package com.zakrodionov.protovary.app.ui.research

import android.view.View
import androidx.navigation.fragment.FragmentNavigator

fun FragmentNavigatorExtras(vararg sharedElements: Pair<View, String>) =
        FragmentNavigator.Extras.Builder().apply {
            sharedElements.forEach { (view, name) ->
                addSharedElement(view, name)
            }
        }.build()