package com.zakrodionov.roskachestvo.data.repository

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.Researches
import com.zakrodionov.roskachestvo.domain.repository.ResearchesRepository

class ResearchesRepositoryImpl : ResearchesRepository {

    override suspend fun getResearches(): Either<Failure, List<Researches>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}