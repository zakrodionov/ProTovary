package com.zakrodionov.roskachestvo.app.ext

import androidx.navigation.NavController

fun NavController.navigateTo(action: Int, destinationId: Int) {
    if (this.currentDestination?.id != destinationId) {
        this.navigate(action)
    }
}