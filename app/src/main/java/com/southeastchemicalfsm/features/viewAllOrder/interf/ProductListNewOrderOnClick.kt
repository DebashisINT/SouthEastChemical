package com.southeastchemicalfsm.features.viewAllOrder.interf

import com.southeastchemicalfsm.app.domain.NewOrderGenderEntity
import com.southeastchemicalfsm.app.domain.NewOrderProductEntity

interface ProductListNewOrderOnClick {
    fun productListOnClick(product: NewOrderProductEntity)
}