package com.southeastchemicalfsm.features.orderList.model

import com.southeastchemicalfsm.base.BaseResponse


class ReturnListResponseModel: BaseResponse() {
    var return_list: ArrayList<ReturnDataModel>? = null
}