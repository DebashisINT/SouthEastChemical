package com.southeastchemicalfsm.features.dashboard.presentation.api.dayStartEnd

import com.southeastchemicalfsm.features.stockCompetetorStock.api.AddCompStockApi
import com.southeastchemicalfsm.features.stockCompetetorStock.api.AddCompStockRepository

object DayStartEndRepoProvider {
    fun dayStartRepositiry(): DayStartEndRepository {
        return DayStartEndRepository(DayStartEndApi.create())
    }

}