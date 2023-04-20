package com.southeastchemicalfsm.features.photoReg.adapter

import com.southeastchemicalfsm.features.photoReg.model.ProsCustom
import com.southeastchemicalfsm.features.photoReg.model.UserListResponseModel

interface ProsListSelectionListner {
    fun getInfo(obj: ProsCustom)
}