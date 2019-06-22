package com.zakrodionov.protovary.data.repository

import com.zakrodionov.protovary.data.db.ResearchDao
import com.zakrodionov.protovary.data.entity.Researches
import com.zakrodionov.protovary.data.network.Api
import com.zakrodionov.protovary.domain.repository.BaseRepository

class ResearchRepository(
    private val api: Api,
    private val researchDao: ResearchDao
) : BaseRepository() {

    suspend fun getResearches() = request(api.getResearches())

    suspend fun getResearch(id: Long) = request(api.getResearch(id))

    suspend fun getResearchesCategory(id: Long): Researches = request(researchDao.getResearchCategoryById(id))
}