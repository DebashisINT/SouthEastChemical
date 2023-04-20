package com.southeastchemicalfsm.features.location.shopRevisitStatus

import com.southeastchemicalfsm.features.location.shopdurationapi.ShopDurationApi
import com.southeastchemicalfsm.features.location.shopdurationapi.ShopDurationRepository

object ShopRevisitStatusRepositoryProvider {
    fun provideShopRevisitStatusRepository(): ShopRevisitStatusRepository {
        return ShopRevisitStatusRepository(ShopRevisitStatusApi.create())
    }
}