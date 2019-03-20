package com.zakrodionov.protovary.app.util

/**
 * Created by Zakhar Rodionov on 20.03.19.
 */
object Utils {
    fun formatBarcode(barcode: String): String {
       return when (barcode.length){
            11 -> { "${barcode.substring(0, 1)} ${barcode.substring(1, 6)} ${barcode.substring(6, 11)}" }
            13 -> { "${barcode.substring(0, 1)} ${barcode.substring(1, 7)} ${barcode.substring(7, 13)}" }
            else -> barcode
        }
    }
}