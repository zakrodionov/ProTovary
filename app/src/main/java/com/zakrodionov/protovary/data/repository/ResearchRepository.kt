package com.zakrodionov.protovary.data.repository

import com.zakrodionov.protovary.data.network.Api

class ResearchRepository(
    private val api: Api
) {

    suspend fun getResearches() = api.getResearches()

    suspend fun getResearch(id: Long) = api.getResearch(id)
}
