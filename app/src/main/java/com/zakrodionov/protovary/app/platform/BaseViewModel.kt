package com.zakrodionov.protovary.app.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*BaseViewModel для обработки State*/
abstract class BaseViewModel : ViewModel() {

    val state: MutableLiveData<Event<State>> = MutableLiveData()
    val message: MutableLiveData<Event<String>> = MutableLiveData()

    protected fun handleState(state: State) {
        this.state.value = Event(state)
    }

    protected fun launch(func: suspend () -> Unit) = viewModelScope.launch(Dispatchers.Main) { func.invoke() }

    protected fun launchIO(func: suspend () -> Unit) = viewModelScope.launch(Dispatchers.IO) { func.invoke() }
}