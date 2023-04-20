package com.southeastchemicalfsm.features.nearbyuserlist.api

import com.southeastchemicalfsm.app.Pref
import com.southeastchemicalfsm.features.nearbyuserlist.model.NearbyUserResponseModel
import com.southeastchemicalfsm.features.newcollection.model.NewCollectionListResponseModel
import com.southeastchemicalfsm.features.newcollection.newcollectionlistapi.NewCollectionListApi
import io.reactivex.Observable

class NearbyUserRepo(val apiService: NearbyUserApi) {
    fun nearbyUserList(): Observable<NearbyUserResponseModel> {
        return apiService.getNearbyUserList(Pref.session_token!!, Pref.user_id!!)
    }
}