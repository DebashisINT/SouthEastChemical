package com.southeastchemicalfsm.features.newcollectionreport

import com.southeastchemicalfsm.features.photoReg.model.UserListResponseModel

interface PendingCollListner {
    fun getUserInfoOnLick(obj: PendingCollData)
}