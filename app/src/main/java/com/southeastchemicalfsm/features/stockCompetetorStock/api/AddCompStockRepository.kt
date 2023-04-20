package com.southeastchemicalfsm.features.stockCompetetorStock.api

import com.southeastchemicalfsm.base.BaseResponse
import com.southeastchemicalfsm.features.orderList.model.NewOrderListResponseModel
import com.southeastchemicalfsm.features.stockCompetetorStock.ShopAddCompetetorStockRequest
import com.southeastchemicalfsm.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class AddCompStockRepository(val apiService:AddCompStockApi){

    fun addCompStock(shopAddCompetetorStockRequest: ShopAddCompetetorStockRequest): Observable<BaseResponse> {
        return apiService.submShopCompStock(shopAddCompetetorStockRequest)
    }

    fun getCompStockList(sessiontoken: String, user_id: String, date: String): Observable<CompetetorStockGetData> {
        return apiService.getCompStockList(sessiontoken, user_id, date)
    }
}