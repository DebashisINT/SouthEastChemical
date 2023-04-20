package com.southeastchemicalfsm.features.viewAllOrder.orderOptimized

data class CommonProductCatagory(var id_sel:String , var name_sel :String)

open class CustomProductRate(var product_id:String="",var product_name:String="",var brand_id:String="",var brand:String="",var category_id:String="",var category:String="",
var watt_id:String="",var watt:String="",var product_mrp_show:String="",var product_discount_show:String="",var rate:String="0.00")

data class ProductQtyRateSubmit(var submitedQty:String = "-1" , var submitedRate:String = "0",var stock_amount:String="0",var stock_unit:String="",
    var isStockShow:Boolean = false,var isRateShow:Boolean = false) : CustomProductRate()

data class FinalOrderData(var product_id:String="",var product_name:String="",var qty:String="0",var rate:String="0.00",var brand_id:String = "",var brand:String="",
                          var category_id:String="",var category:String="",var watt_id:String="",var watt:String="",var product_mrp_show:String="",var product_discount_show:String="")
data class FinalOrderDataWithShopID(var shop_id:String , var ordList:ArrayList<FinalOrderData>)

