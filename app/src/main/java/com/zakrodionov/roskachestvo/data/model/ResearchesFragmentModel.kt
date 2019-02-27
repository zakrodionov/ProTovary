package com.zakrodionov.roskachestvo.data.model

import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import java.io.Serializable

data class ResearchesFragmentModel(val researches: List<ResearchCompact>?) : Serializable