package com.southeastchemicalfsm.features.viewAllOrder.interf

import com.southeastchemicalfsm.app.domain.NewOrderGenderEntity
import com.southeastchemicalfsm.features.viewAllOrder.model.ProductOrder
import java.text.FieldPosition

interface NewOrderSizeQtyDelOnClick {
    fun sizeQtySelListOnClick(product_size_qty: ArrayList<ProductOrder>)
    fun sizeQtyListOnClick(product_size_qty: ProductOrder,position: Int)
}