package com.zakrodionov.protovary.app.ui.base

import androidx.annotation.LayoutRes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Layout(
    /**
     * The R.layout.* field which refer to the layout.
     *
     * @return the id of the layout
     */
    @LayoutRes val value: Int = -1
)