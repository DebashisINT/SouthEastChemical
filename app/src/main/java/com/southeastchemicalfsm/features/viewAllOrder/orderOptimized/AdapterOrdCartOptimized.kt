package com.southeastchemicalfsm.features.viewAllOrder.orderOptimized

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.southeastchemicalfsm.R
import com.southeastchemicalfsm.app.Pref
import com.southeastchemicalfsm.app.utils.AppUtils
import com.southeastchemicalfsm.app.utils.CustomSpecialTextWatcher1
import com.southeastchemicalfsm.app.utils.Toaster
import com.southeastchemicalfsm.app.utils.ToasterMiddle
import com.southeastchemicalfsm.features.DecimalDigitsInputFilter
import com.southeastchemicalfsm.features.dashboard.presentation.DashboardActivity
import com.southeastchemicalfsm.widgets.AppCustomTextView
import kotlinx.android.synthetic.main.row_ord_opti_cart_list.view.*
import kotlinx.android.synthetic.main.row_ord_opti_product_list.view.*

class AdapterOrdCartOptimized(val mContext:Context,val cartL:ArrayList<FinalOrderData>,val listner:OnRateQtyOptiOnClick):
    RecyclerView.Adapter<AdapterOrdCartOptimized.OrdCartOptimizedViewHolder>(){

    var isRateChanging = false
    var isDiscChanging = false
    var isQtyChanging = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdCartOptimizedViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_ord_opti_cart_list,parent,false)
        return OrdCartOptimizedViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdCartOptimizedViewHolder, position: Int) {
        holder.bindItems()
    }

    override fun getItemCount(): Int {
        return cartL.size
    }

    inner class OrdCartOptimizedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindItems(){
            itemView.tv_row_ord_opti_cart_product_name.text = cartL.get(adapterPosition).product_name
            itemView.et_row_ord_opti_cart_rate.setText(cartL.get(adapterPosition).rate)
            itemView.et_row_ord_opti_cart_qty.setText(cartL.get(adapterPosition).qty)

            if(Pref.IsViewMRPInOrder){
                itemView.ll_row_ord_opti_cart_mrp_root.visibility = View.VISIBLE
                itemView.tv_row_ord_opti_cart_mrp.setText(cartL.get(adapterPosition).product_mrp_show)
            }else{
                itemView.ll_row_ord_opti_cart_mrp_root.visibility = View.GONE
            }
            if(Pref.IsDiscountInOrder){
                itemView.ll_row_ord_opti_cart_discount_root.visibility = View.VISIBLE
                itemView.et_row_ord_opti_cart_discount.setText(cartL.get(adapterPosition).product_discount_show)
            }else{
                itemView.ll_row_ord_opti_cart_discount_root.visibility = View.GONE
            }
            if(Pref.IsViewMRPInOrder && Pref.IsDiscountInOrder){
                itemView.ll_row_ord_opti_cart_mrp_discount_root.visibility = View.VISIBLE
            }else if(!Pref.IsViewMRPInOrder){
                itemView.ll_row_ord_opti_cart_mrp_discount_root.visibility = View.GONE
            }
            //itemView.et_row_ord_opti_cart_rate.filters=(arrayOf<InputFilter>(DecimalDigitsInputFilter(5, 2)))
            if(Pref.isRateNotEditable){
                itemView.et_row_ord_opti_cart_rate.isEnabled = false
            }else{
                itemView.et_row_ord_opti_cart_rate.isEnabled = true
            }

            /*if(Pref.IsViewMRPInOrder && Pref.IsDiscountInOrder){
                try{
                    var discPrice = cartL.get(adapterPosition).product_mrp_show.toDouble() * (100-cartL.get(adapterPosition).product_discount_show.toDouble()) / 100
                    itemView.et_row_ord_opti_cart_rate.setText(String.format("%.2f",discPrice))
                }catch (ex:Exception){
                    itemView.et_row_ord_opti_cart_rate.setText("0")
                }
            }*/
            itemView.et_row_ord_opti_cart_rate.clearFocus()
            itemView.et_row_ord_opti_cart_qty.clearFocus()
            itemView.et_row_ord_opti_cart_discount.clearFocus()

            itemView.et_row_ord_opti_cart_rate.setError(null)
            itemView.et_row_ord_opti_cart_qty.setError(null)
            itemView.et_row_ord_opti_cart_discount.setError(null)
            itemView.iv_row_ord_opti_cart_tick.visibility = View.GONE

            itemView.iv_row_ord_opti_cart_tick.setOnClickListener {
                AppUtils.hideSoftKeyboard(mContext as DashboardActivity)
                var changRateStr = ""
                var changDiscStr = ""
                var changQtyStr = ""
                try{
                    changRateStr = String.format("%.2f",itemView.et_row_ord_opti_cart_rate.text.toString().toDouble())
                }catch (ex:Exception){
                    changRateStr = "0"
                }
                try{
                    changDiscStr = String.format("%.2f",itemView.et_row_ord_opti_cart_discount.text.toString().toDouble())
                }catch (ex:Exception){
                    changDiscStr = "0"
                }
                try{
                    var qtyRectify:Double = String.format("%.3f",itemView.et_row_ord_opti_cart_qty.text.toString().toDouble()).toDouble()
                    if(qtyRectify-qtyRectify.toInt() == 0.0){
                        changQtyStr = qtyRectify.toInt().toString()
                    }else{
                        changQtyStr = qtyRectify.toString()
                    }
                    //changQtyStr = String.format("%.3f",itemView.et_row_ord_opti_cart_qty.text.toString().toDouble())
                }catch (ex:Exception){
                    changQtyStr = "0"
                }

                if(itemView.et_row_ord_opti_cart_qty.text.toString().length==0 || itemView.et_row_ord_opti_cart_qty.text.toString().toDouble() == 0.0){
                    itemView.et_row_ord_opti_cart_qty.setError("Please enter valid quantity.")
                    ToasterMiddle.msgShort(mContext,"Please enter valid quantity.")
                    return@setOnClickListener
                }

                if(!Pref.IsAllowZeroRateOrder){
                    if(itemView.et_row_ord_opti_cart_rate.text.toString().length==0 || itemView.et_row_ord_opti_cart_rate.text.toString().toDouble() == 0.0){
                        itemView.et_row_ord_opti_cart_rate.setError("Please enter valid rate.")
                        ToasterMiddle.msgShort(mContext,"Please enter valid rate.")
                        return@setOnClickListener
                    }
                }

                if(isQtyChanging){
                    cartL.get(adapterPosition).qty = changQtyStr
                    listner.onQtyChangeClick(cartL.get(adapterPosition).product_id, changQtyStr)
                    notifyDataSetChanged()
                }else if(Pref.IsViewMRPInOrder && Pref.IsDiscountInOrder){
                    try{
                        if(isRateChanging){
                            changDiscStr = String.format("%.2f",(100-((changRateStr.toString().toDouble()*100)/cartL.get(adapterPosition).product_mrp_show.toDouble())))
                            if(changDiscStr.toDouble()<0){
                                ToasterMiddle.msgShort(mContext,"Rate can't be greater than MRP.")
                                return@setOnClickListener
                            }
                        }else if(isDiscChanging){
                            changRateStr = String.format("%.2f",(cartL.get(adapterPosition).product_mrp_show.toDouble() * (100-changDiscStr.toDouble()) / 100))
                        }
                    }catch (ex:Exception){
                        ex.printStackTrace()
                    }
                    cartL.get(adapterPosition).rate = changRateStr
                    cartL.get(adapterPosition).product_discount_show = changDiscStr
                    listner.onDiscountChangeClick(cartL.get(adapterPosition).product_id, changDiscStr, changRateStr)
                    notifyDataSetChanged()
                }else{
                    cartL.get(adapterPosition).rate = changRateStr
                    listner.onRateChangeClick(cartL.get(adapterPosition).product_id,if(changRateStr.equals("")) "0" else changRateStr)
                    notifyDataSetChanged()
                }
            }

            itemView.et_row_ord_opti_cart_rate.setOnFocusChangeListener(object :View.OnFocusChangeListener{
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if(hasFocus){
                        itemView.et_row_ord_opti_cart_rate.addTextChangedListener(
                            CustomSpecialTextWatcher1(itemView.et_row_ord_opti_cart_rate, 5, 2, object : CustomSpecialTextWatcher1.GetCustomTextChangeListener {
                                override fun beforeTextChange(text: String) {

                                }

                                override fun customTextChange(text: String) {
                                    isRateChanging = true
                                    isDiscChanging = false
                                    isQtyChanging = false
                                    itemView.iv_row_ord_opti_cart_tick.visibility = View.VISIBLE
                                }
                            })
                        )
                    }else{
                        itemView.iv_row_ord_opti_cart_tick.visibility = View.GONE
                        AppUtils.hideSoftKeyboard(mContext as DashboardActivity)
                    }
                }
            })
            itemView.et_row_ord_opti_cart_discount.setOnFocusChangeListener(object :View.OnFocusChangeListener{
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if(hasFocus){
                        itemView.et_row_ord_opti_cart_discount.addTextChangedListener(
                            CustomSpecialTextWatcher1(itemView.et_row_ord_opti_cart_discount, 2, 2, object : CustomSpecialTextWatcher1.GetCustomTextChangeListener {
                                override fun beforeTextChange(text: String) {

                                }

                                override fun customTextChange(text: String) {
                                    isRateChanging = false
                                    isDiscChanging = true
                                    isQtyChanging = false
                                    itemView.iv_row_ord_opti_cart_tick.visibility = View.VISIBLE
                                }
                            })
                        )
                    }else{
                        itemView.iv_row_ord_opti_cart_tick.visibility = View.GONE
                        AppUtils.hideSoftKeyboard(mContext as DashboardActivity)
                    }
                }
            })


            itemView.et_row_ord_opti_cart_qty.setOnFocusChangeListener(object :View.OnFocusChangeListener{
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if(hasFocus){
                        itemView.et_row_ord_opti_cart_qty.addTextChangedListener(
                            CustomSpecialTextWatcher1(itemView.et_row_ord_opti_cart_qty, 5, 3, object : CustomSpecialTextWatcher1.GetCustomTextChangeListener {
                                override fun beforeTextChange(text: String) {

                                }

                                override fun customTextChange(text: String) {
                                    isRateChanging = false
                                    isDiscChanging = false
                                    isQtyChanging = true
                                    itemView.iv_row_ord_opti_cart_tick.visibility = View.VISIBLE
                                }
                            })
                        )
                    }else{
                        itemView.iv_row_ord_opti_cart_tick.visibility = View.GONE
                        AppUtils.hideSoftKeyboard(mContext as DashboardActivity)
                    }
                }
            })

            //delete section
            itemView.iv_row_ord_opti_cart_del.setOnClickListener {
                val simpleDialogg = Dialog(mContext)
                simpleDialogg.setCancelable(false)
                simpleDialogg.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                simpleDialogg.setContentView(R.layout.dialog_yes_no)
                val dialogHeader = simpleDialogg.findViewById(R.id.dialog_yes_no_headerTV) as AppCustomTextView
                val dialogBody = simpleDialogg.findViewById(R.id.dialog_cancel_order_header_TV) as AppCustomTextView
                dialogHeader.text=cartL.get(adapterPosition).product_name
                dialogBody.text="Wish to Remove the Product from the Cart?" //mantis 25788
                val dialogYes = simpleDialogg.findViewById(R.id.tv_dialog_yes_no_yes) as AppCustomTextView
                val dialogNo = simpleDialogg.findViewById(R.id.tv_dialog_yes_no_no) as AppCustomTextView
                dialogYes.setOnClickListener {
                    simpleDialogg.dismiss()
                    cartL.removeAt(adapterPosition)
                    listner.onDelChangeClick(cartL.size)
                    notifyDataSetChanged()
                }
                dialogNo.setOnClickListener {
                    simpleDialogg.dismiss()
                }
                simpleDialogg.show()
            }
        }
    }

    interface OnRateQtyOptiOnClick {
        fun onRateChangeClick(productID:String,changingRate:String)
        fun onQtyChangeClick(productID:String,changingQty:String)
        fun onDiscountChangeClick(productID:String,changingDisc:String,changingRate: String)
        fun onDelChangeClick(cartSize:Int)
    }

}