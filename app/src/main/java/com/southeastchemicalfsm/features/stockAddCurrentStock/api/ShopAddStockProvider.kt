package com.southeastchemicalfsm.features.stockAddCurrentStock.api

import com.southeastchemicalfsm.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.southeastchemicalfsm.features.location.shopRevisitStatus.ShopRevisitStatusRepository

object ShopAddStockProvider {
    fun provideShopAddStockRepository(): ShopAddStockRepository {
        return ShopAddStockRepository(ShopAddStockApi.create())
    }
}