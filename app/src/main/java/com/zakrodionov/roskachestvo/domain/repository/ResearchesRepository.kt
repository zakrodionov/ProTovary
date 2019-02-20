package com.zakrodionov.roskachestvo.domain.repository

import com.zakrodionov.roskachestvo.domain.entity.Researches
import io.reactivex.Single

interface ResearchesRepository {

    fun getResearches(): ArrayList<Researches>
}