package com.southeastchemicalfsm.features.viewAllOrder.model

import com.southeastchemicalfsm.app.domain.NewOrderColorEntity
import com.southeastchemicalfsm.app.domain.NewOrderGenderEntity
import com.southeastchemicalfsm.app.domain.NewOrderProductEntity
import com.southeastchemicalfsm.app.domain.NewOrderSizeEntity
import com.southeastchemicalfsm.features.stockCompetetorStock.model.CompetetorStockGetDataDtls

class NewOrderDataModel {
    var status:String ? = null
    var message:String ? = null
    var Gender_list :ArrayList<NewOrderGenderEntity>? = null
    var Product_list :ArrayList<NewOrderProductEntity>? = null
    var Color_list :ArrayList<NewOrderColorEntity>? = null
    var size_list :ArrayList<NewOrderSizeEntity>? = null
}

