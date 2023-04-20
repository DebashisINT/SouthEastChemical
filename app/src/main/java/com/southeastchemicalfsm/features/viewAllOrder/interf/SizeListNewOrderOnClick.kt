package com.southeastchemicalfsm.features.viewAllOrder.interf

import com.southeastchemicalfsm.app.domain.NewOrderProductEntity
import com.southeastchemicalfsm.app.domain.NewOrderSizeEntity

interface SizeListNewOrderOnClick {
    fun sizeListOnClick(size: NewOrderSizeEntity)
}