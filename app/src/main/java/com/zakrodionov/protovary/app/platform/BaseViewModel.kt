package com.zakrodionov.protovary.app.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    var failure: MutableLiveData<Failure> = SingleLiveEvent()
    var message: MutableLiveData<String> = SingleLiveEvent()

    protected fun handleFailure(failure: Failure) {
        loading.value = false
        this.failure.value = failure
    }
}