package com.southeastchemicalfsm.features.login.model.productlistmodel

import com.southeastchemicalfsm.app.domain.ModelEntity
import com.southeastchemicalfsm.app.domain.ProductListEntity
import com.southeastchemicalfsm.base.BaseResponse

class ModelListResponse: BaseResponse() {
    var model_list: ArrayList<ModelEntity>? = null
}