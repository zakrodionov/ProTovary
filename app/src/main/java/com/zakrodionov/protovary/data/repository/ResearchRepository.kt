package com.zakrodionov.protovary.data.repository

import com.zakrodionov.protovary.data.db.ResearchDao
import com.zakrodionov.protovary.data.entity.Researches
import com.zakrodionov.protovary.data.network.Api

class ResearchRepository(
    private val api: Api,
    private val researchDao: ResearchDao
) {

    suspend fun getResearches() = api.getResearches()

    suspend fun getResearch(id: Long) = api.getResearch(id)

    suspend fun getResearchesCategory(id: Long): Researches = researchDao.getResearchCategoryById(id)
}