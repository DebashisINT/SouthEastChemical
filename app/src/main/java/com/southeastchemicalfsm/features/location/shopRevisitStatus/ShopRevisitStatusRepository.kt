package com.southeastchemicalfsm.features.location.shopRevisitStatus

import com.southeastchemicalfsm.base.BaseResponse
import com.southeastchemicalfsm.features.location.model.ShopDurationRequest
import com.southeastchemicalfsm.features.location.model.ShopRevisitStatusRequest
import io.reactivex.Observable

class ShopRevisitStatusRepository(val apiService : ShopRevisitStatusApi) {
    fun shopRevisitStatus(shopRevisitStatus: ShopRevisitStatusRequest?): Observable<BaseResponse> {
        return apiService.submShopRevisitStatus(shopRevisitStatus)
    }
}