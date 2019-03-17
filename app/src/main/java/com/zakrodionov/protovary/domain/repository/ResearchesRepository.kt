package com.zakrodionov.protovary.domain.repository

import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.domain.entity.Research
import com.zakrodionov.protovary.domain.entity.ResearchesCategory

interface ResearchesRepository {

    suspend fun getResearches(): Either<Failure, List<ResearchesCategory>>

    suspend fun getResearch(id: Long): Either<Failure, Research>

    suspend fun getResearchesCategory(id: Long): Either<Failure, ResearchesCategory>
}