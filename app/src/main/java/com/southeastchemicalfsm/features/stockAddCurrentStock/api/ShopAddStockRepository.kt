package com.southeastchemicalfsm.features.stockAddCurrentStock.api

import com.southeastchemicalfsm.base.BaseResponse
import com.southeastchemicalfsm.features.location.model.ShopRevisitStatusRequest
import com.southeastchemicalfsm.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.southeastchemicalfsm.features.stockAddCurrentStock.ShopAddCurrentStockRequest
import com.southeastchemicalfsm.features.stockAddCurrentStock.model.CurrentStockGetData
import com.southeastchemicalfsm.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class ShopAddStockRepository (val apiService : ShopAddStockApi){
    fun shopAddStock(shopAddCurrentStockRequest: ShopAddCurrentStockRequest?): Observable<BaseResponse> {
        return apiService.submShopAddStock(shopAddCurrentStockRequest)
    }

    fun getCurrStockList(sessiontoken: String, user_id: String, date: String): Observable<CurrentStockGetData> {
        return apiService.getCurrStockListApi(sessiontoken, user_id, date)
    }

}