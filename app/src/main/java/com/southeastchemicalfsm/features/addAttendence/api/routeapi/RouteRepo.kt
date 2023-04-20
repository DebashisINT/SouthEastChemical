package com.southeastchemicalfsm.features.addAttendence.api.routeapi

import com.southeastchemicalfsm.app.Pref
import com.southeastchemicalfsm.features.addAttendence.model.DistanceResponseModel
import com.southeastchemicalfsm.features.addAttendence.model.LocationListResponseModel
import com.southeastchemicalfsm.features.addAttendence.model.RouteResponseModel
import io.reactivex.Observable

/**
 * Created by Saikat on 22-11-2018.
 */
class RouteRepo(val apiService: RouteApi) {
    fun getRouteList(): Observable<RouteResponseModel> {
        return apiService.getRouteList(Pref.session_token!!, Pref.user_id!!)
    }

    fun getLocList(): Observable<LocationListResponseModel> {
        return apiService.getLocationList(Pref.session_token!!, Pref.user_id!!)
    }

    fun getDistance(from_id: String, to_id: String): Observable<DistanceResponseModel> {
        return apiService.getDistance(Pref.session_token!!, Pref.user_id!!, from_id, to_id)
    }
}