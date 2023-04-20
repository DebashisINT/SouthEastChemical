package com.southeastchemicalfsm.features.viewAllOrder.orderOptimized

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.InputFilter
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.southeastchemicalfsm.R
import com.southeastchemicalfsm.app.Pref
import com.southeastchemicalfsm.app.utils.*
import com.southeastchemicalfsm.features.DecimalDigitsInputFilter
import com.southeastchemicalfsm.features.dashboard.presentation.DashboardActivity
import com.southeastchemicalfsm.widgets.AppCustomTextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.customnotification.view.*
import kotlinx.android.synthetic.main.row_ord_opti_cart_list.view.*
import kotlinx.android.synthetic.main.row_ord_opti_product_list.view.*


class AdapterOrdProductOptimized(val mContext: Context,var proList : ArrayList<ProductQtyRateSubmit>,var finalOrderDataList : ArrayList<FinalOrderData>, var listner :OnProductOptiOnClick):
    RecyclerView.Adapter<AdapterOrdProductOptimized.OrdProductOptimizedViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdProductOptimizedViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_ord_opti_product_list,parent,false)
        return OrdProductOptimizedViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdProductOptimizedViewHolder, position: Int) {
        holder.bindItems(mContext,proList,listner)
    }

    override fun getItemCount(): Int {
        return proList.size!!
    }

    inner class OrdProductOptimizedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItems(context: Context,prooductList:ArrayList<ProductQtyRateSubmit>,listner :OnProductOptiOnClick){

            itemView.tv_row_ord_opti_product_list_brand.text = prooductList.get(adapterPosition).brand.toString()
            itemView.tv_row_ord_opti_product_list_measure.text = prooductList.get(adapterPosition).watt.toString()
            itemView.tv_row_ord_opti_product_list_catagory.text = prooductList.get(adapterPosition).category.toString()
            itemView.tv_row_ord_opti_product_list_name.text = prooductList.get(adapterPosition).product_name.toString()
            itemView.tv_row_ord_opti_product_list_rate.setText(prooductList.get(adapterPosition).rate.toString())

            if(Pref.IsViewMRPInOrder){
                itemView.ll_row_ord_pro_list_mrp_root.visibility = View.VISIBLE
                itemView.tv_row_ord_pro_list_mrp.setText("₹ "+prooductList.get(adapterPosition).product_mrp_show)
            }else{
                itemView.ll_row_ord_pro_list_mrp_root.visibility = View.GONE
            }
            if(Pref.IsDiscountInOrder){
                itemView.ll_row_ord_pro_list_discount_root.visibility = View.VISIBLE
                itemView.tv_row_ord_pro_list_discount.setText(prooductList.get(adapterPosition).product_discount_show)
            }else{
                itemView.ll_row_ord_pro_list_discount_root.visibility = View.GONE
            }
            if(Pref.IsViewMRPInOrder && Pref.IsDiscountInOrder){
                itemView.ll_row_ord_pro_list_mrp_discount_root.visibility = View.VISIBLE
                try{
                    var discPrice = prooductList.get(adapterPosition).product_mrp_show.toDouble() * (100-prooductList.get(adapterPosition).product_discount_show.toDouble()) / 100
                    itemView.tv_row_ord_opti_product_list_rate.setText(String.format("%.2f",discPrice))
                }catch (ex:Exception){
                    itemView.tv_row_ord_opti_product_list_rate.setText("0")
                }
            }else if(!Pref.IsViewMRPInOrder){
                itemView.ll_row_ord_pro_list_mrp_discount_root.visibility = View.GONE
            }
            if(Pref.isRateNotEditable){
                itemView.tv_row_ord_opti_product_list_rate.isEnabled = false
            }else{
                itemView.tv_row_ord_opti_product_list_rate.isEnabled = true
            }

            itemView.tv_row_ord_opti_product_list_qty.clearFocus()
            itemView.tv_row_ord_opti_product_list_rate.clearFocus()
            itemView.tv_row_ord_pro_list_discount.clearFocus()

            itemView.tv_row_ord_opti_product_list_qty.setError(null)
            itemView.tv_row_ord_opti_product_list_rate.setError(null)
            itemView.tv_row_ord_pro_list_discount.setError(null)

            if((finalOrderDataList.map { it.product_id } as ArrayList<String>).contains(prooductList.get(adapterPosition).product_id)){
                prooductList.get(adapterPosition).submitedQty = finalOrderDataList.filter { prooductList.get(adapterPosition).product_id.equals(it.product_id) }.first().qty
            }else{
                prooductList.get(adapterPosition).submitedQty = "-1"
            }

            if(prooductList.get(adapterPosition).submitedQty.equals("-1")){
                itemView.tv_row_ord_opti_product_list_qty.setText("")
                if(!Pref.IsViewMRPInOrder && !Pref.IsDiscountInOrder){
                    itemView.tv_row_ord_opti_product_list_rate.setText(prooductList.get(adapterPosition).rate)
                }
                itemView.tv_row_ord_opti_product_list_add_text.text = "Add"
                itemView.ll_row_ord_opti_product_list_add_text_root.background.setTint(mContext.getResources().getColor(R.color.color_custom_blue))
                itemView.iv_row_ord_opti_product_list_add_img.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.icon_shopping))
            }else{
                itemView.tv_row_ord_opti_product_list_qty.setText(prooductList.get(adapterPosition).submitedQty)
                itemView.tv_row_ord_opti_product_list_rate.setText(finalOrderDataList.filter { it.product_id.equals(prooductList.get(adapterPosition).product_id) }.first().rate.toString())
                itemView.tv_row_ord_opti_product_list_add_text.text = "Added"
                itemView.ll_row_ord_opti_product_list_add_text_root.background.setTint(mContext.getResources().getColor(R.color.color_custom_green))
                itemView.iv_row_ord_opti_product_list_add_img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_tick_nw))
            }

            itemView.cv_row_ord_pro_list_shop_add_product.setOnClickListener {
                if(itemView.tv_row_ord_opti_product_list_qty.text.toString().length==0 || itemView.tv_row_ord_opti_product_list_qty.text.toString().toDouble() == 0.0){
                    itemView.tv_row_ord_opti_product_list_qty.setError("Please enter valid quantity.")
                    ToasterMiddle.msgShort(mContext,"Please enter valid quantity.")
                    return@setOnClickListener
                }
                if(!Pref.IsAllowZeroRateOrder){
                    if(itemView.tv_row_ord_opti_product_list_rate.text.toString().length==0 || itemView.tv_row_ord_opti_product_list_rate.text.toString().toDouble() == 0.0){
                        itemView.tv_row_ord_opti_product_list_rate.setError("Please enter valid rate.")
                        ToasterMiddle.msgShort(mContext,"Please enter valid rate.")
                        return@setOnClickListener
                    }
                }

                var changingRate = "0.0"
                var changingDisc = "0.0"
                try{
                    changingRate = if(itemView.tv_row_ord_opti_product_list_rate.text.toString().equals("")) "0.00" else itemView.tv_row_ord_opti_product_list_rate.text.toString()
                }catch (ex:Exception){
                    changingRate = "0.0"
                }
                if(Pref.IsViewMRPInOrder && Pref.IsDiscountInOrder){
                    if(changingRate.toDouble()>prooductList.get(adapterPosition).product_mrp_show.toDouble()){
                        ToasterMiddle.msgShort(mContext,"Rate can't be greater than MRP.")
                        return@setOnClickListener
                    }
                    changingDisc = String.format("%.2f",(100-((changingRate.toString().toDouble()*100)/prooductList.get(adapterPosition).product_mrp_show.toDouble())))
                }

                if((finalOrderDataList.filter { it.product_id.equals(prooductList.get(adapterPosition).product_id)} as ArrayList<FinalOrderData>).size>0 ){
                    finalOrderDataList.filter { it.product_id.equals(prooductList.get(adapterPosition).product_id)}.first().qty=itemView.tv_row_ord_opti_product_list_qty.text.toString()
                    finalOrderDataList.filter { it.product_id.equals(prooductList.get(adapterPosition).product_id)}.first().rate=if(itemView.tv_row_ord_opti_product_list_rate.text.toString().equals("")) "0.00" else itemView.tv_row_ord_opti_product_list_rate.text.toString()
                    if(Pref.IsViewMRPInOrder && Pref.IsDiscountInOrder){
                        finalOrderDataList.filter { it.product_id.equals(prooductList.get(adapterPosition).product_id)}.first().product_discount_show=changingDisc
                    }
                    ToasterMiddle.msgShort(mContext,"Product updated.")
                    //notifyDataSetChanged()
                }else{
                    var obj = FinalOrderData()
                    obj.apply {
                        product_id = prooductList.get(adapterPosition).product_id
                        product_name = prooductList.get(adapterPosition).product_name
                        qty = if(itemView.tv_row_ord_opti_product_list_qty.text.toString().equals("")) "0" else itemView.tv_row_ord_opti_product_list_qty.text.toString()
                        rate = if(itemView.tv_row_ord_opti_product_list_rate.text.toString().equals("")) "0.00" else itemView.tv_row_ord_opti_product_list_rate.text.toString()
                        brand_id = prooductList.get(adapterPosition).brand_id
                        brand = prooductList.get(adapterPosition).brand
                        category_id = prooductList.get(adapterPosition).category_id
                        category = prooductList.get(adapterPosition).category
                        watt_id = prooductList.get(adapterPosition).watt_id
                        watt = prooductList.get(adapterPosition).watt
                        product_mrp_show = prooductList.get(adapterPosition).product_mrp_show
                        product_discount_show = changingDisc
                    }
                    finalOrderDataList.add(obj)
                    prooductList.get(adapterPosition).submitedQty = obj.qty

                    itemView.tv_row_ord_opti_product_list_add_text.text = "Added"
                    itemView.ll_row_ord_opti_product_list_add_text_root.background.setTint(mContext.getResources().getColor(R.color.color_custom_green))

                    ToasterMiddle.msgShort(mContext,prooductList.get(adapterPosition).product_name + " added.")
                    //notifyDataSetChanged()
                }
                listner.onProductAddClick(finalOrderDataList.size,finalOrderDataList.sumOf { it.rate.toDouble() * it.qty.toDouble() })
            }

            itemView.tv_row_ord_opti_product_list_qty.setOnFocusChangeListener(object :View.OnFocusChangeListener{
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if(hasFocus){
                        itemView.tv_row_ord_opti_product_list_qty.addTextChangedListener(
                            CustomSpecialTextWatcher1(itemView.tv_row_ord_opti_product_list_qty, 5, 3, object : CustomSpecialTextWatcher1.GetCustomTextChangeListener {
                                override fun beforeTextChange(text: String) {

                                }

                                override fun customTextChange(text: String) {

                                }
                            })
                        )
                    }else{
                        AppUtils.hideSoftKeyboard(mContext as DashboardActivity)
                    }
                }
            })

            itemView.tv_row_ord_opti_product_list_rate.setOnFocusChangeListener(object :View.OnFocusChangeListener{
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if(hasFocus){
                        try{
                            var prevRateText = itemView.tv_row_ord_opti_product_list_rate.text.toString()
                            if(prevRateText.toDouble() == 0.0){
                                itemView.tv_row_ord_opti_product_list_rate.setText("")
                            }
                        }catch (ex:Exception){
                            ex.printStackTrace()
                        }

                        itemView.tv_row_ord_opti_product_list_rate.addTextChangedListener(
                            CustomSpecialTextWatcher1(itemView.tv_row_ord_opti_product_list_rate, 5, 2, object : CustomSpecialTextWatcher1.GetCustomTextChangeListener {
                                override fun beforeTextChange(text: String) {

                                }

                                override fun customTextChange(text: String) {

                                }
                            })
                        )
                    }else{
                        AppUtils.hideSoftKeyboard(mContext as DashboardActivity)
                    }
                }
            })

            itemView.tv_row_ord_pro_list_discount.setOnFocusChangeListener(object :View.OnFocusChangeListener{
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if(hasFocus){
                        itemView.tv_row_ord_pro_list_discount.addTextChangedListener(
                            CustomSpecialTextWatcher1(itemView.tv_row_ord_pro_list_discount, 2, 2, object : CustomSpecialTextWatcher1.GetCustomTextChangeListener {
                                override fun beforeTextChange(text: String) {

                                }

                                override fun customTextChange(text: String) {
                                    try{
                                        var discPrice = prooductList.get(adapterPosition).product_mrp_show.toDouble() * (100-itemView.tv_row_ord_pro_list_discount.text.toString().toDouble()) / 100
                                        itemView.tv_row_ord_opti_product_list_rate.setText(String.format("%.2f",discPrice))
                                    }catch (ex:Exception){
                                        itemView.tv_row_ord_opti_product_list_rate.setText("0")
                                    }
                                }
                            })
                        )
                    }else{
                        AppUtils.hideSoftKeyboard(mContext as DashboardActivity)
                    }
                }
            })

        }
    }

    interface OnProductOptiOnClick {
        fun onProductAddClick(productCount:Int,sumAmt:Double)
    }

}