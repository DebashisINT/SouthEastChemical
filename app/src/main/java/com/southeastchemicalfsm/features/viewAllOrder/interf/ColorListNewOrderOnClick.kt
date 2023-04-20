package com.southeastchemicalfsm.features.viewAllOrder.interf

import com.southeastchemicalfsm.app.domain.NewOrderColorEntity
import com.southeastchemicalfsm.app.domain.NewOrderProductEntity

interface ColorListNewOrderOnClick {
    fun productListOnClick(color: NewOrderColorEntity)
}