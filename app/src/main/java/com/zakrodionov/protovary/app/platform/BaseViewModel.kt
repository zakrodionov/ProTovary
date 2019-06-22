package com.zakrodionov.protovary.app.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*BaseViewModel для обработки State*/
abstract class BaseViewModel : ViewModel() {

    val state: MutableLiveData<State> = MutableLiveData()
    var message: MutableLiveData<String> = SingleLiveEvent()

    protected fun handleState(state: State) {
        this.state.value = state
    }

    protected fun launch(func: suspend () -> Unit) = viewModelScope.launch(Dispatchers.Main) { func.invoke() }

    protected fun launchIO(func: suspend () -> Unit) = viewModelScope.launch(Dispatchers.IO) { func.invoke() }
}