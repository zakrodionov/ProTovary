package com.zakrodionov.protovary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zakrodionov.protovary.domain.entity.ResearchCompact
import com.zakrodionov.protovary.domain.entity.Researches

@Dao
interface ResearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResearchesCategory(researches: List<Researches>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResearchesCompact(researches: List<ResearchCompact>)

    @Query("SELECT * FROM researchcompact")
    suspend fun getResearchesCompact(): List<ResearchCompact>

    @Query("SELECT * FROM researches WHERE id = :id")
    suspend fun getResearchCategoryById(id: Long): Researches
}