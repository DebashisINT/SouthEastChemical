package com.southeastchemicalfsm.features.location.api

import com.southeastchemicalfsm.features.location.shopdurationapi.ShopDurationApi
import com.southeastchemicalfsm.features.location.shopdurationapi.ShopDurationRepository


object LocationRepoProvider {
    fun provideLocationRepository(): LocationRepo {
        return LocationRepo(LocationApi.create())
    }
}