package com.southeastchemicalfsm.features.viewAllOrder.orderOptimized

import com.southeastchemicalfsm.app.domain.ProductOnlineRateTempEntity
import com.southeastchemicalfsm.base.BaseResponse
import com.southeastchemicalfsm.features.login.model.productlistmodel.ProductRateDataModel
import java.io.Serializable

class ProductRateOnlineListResponseModel: BaseResponse(), Serializable {
    var product_rate_list: ArrayList<ProductOnlineRateTempEntity>? = null
}