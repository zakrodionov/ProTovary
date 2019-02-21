package com.zakrodionov.roskachestvo.domain.repository

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.Researches

interface ResearchesRepository {

    suspend fun getResearches(): Either<Failure, List<Researches>>
}