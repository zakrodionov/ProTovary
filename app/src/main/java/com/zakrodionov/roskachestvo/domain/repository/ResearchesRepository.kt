package com.zakrodionov.roskachestvo.domain.repository

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.Research
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory

interface ResearchesRepository {

    suspend fun getResearches(): Either<Failure, List<ResearchesCategory>>

    suspend fun getResearch(id: Long): Either<Failure, Research>
}