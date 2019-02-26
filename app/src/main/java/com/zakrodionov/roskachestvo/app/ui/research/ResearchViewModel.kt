package com.zakrodionov.roskachestvo.app.ui.research

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.domain.entity.Researches
import com.zakrodionov.roskachestvo.domain.interactor.UseCase
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearches
import javax.inject.Inject

class ResearchViewModel @Inject constructor(val getResearches: GetResearches) : BaseViewModel() {

    val researches = MutableLiveData<List<Researches>>()

    fun loadResearches() = getResearches.invoke(UseCase.None()){ it.either(::handleFailure, ::handleResearches) }

    private fun handleResearches(list: List<Researches>){
        researches.value = list
    }
}