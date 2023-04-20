package com.southeastchemicalfsm.features.photoReg.present

import com.southeastchemicalfsm.app.domain.ProspectEntity
import com.southeastchemicalfsm.features.photoReg.model.UserListResponseModel

interface DsStatusListner {
    fun getDSInfoOnLick(obj: ProspectEntity)
}