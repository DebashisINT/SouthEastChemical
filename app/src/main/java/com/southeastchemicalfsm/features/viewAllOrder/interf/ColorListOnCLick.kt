package com.southeastchemicalfsm.features.viewAllOrder.interf

import com.southeastchemicalfsm.app.domain.NewOrderGenderEntity
import com.southeastchemicalfsm.features.viewAllOrder.model.ProductOrder

interface ColorListOnCLick {
    fun colorListOnCLick(size_qty_list: ArrayList<ProductOrder>, adpPosition:Int)
}