package com.southeastchemicalfsm.features.performanceAPP

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.southeastchemicalfsm.R
import kotlinx.android.synthetic.main.row_party_notvisited_list.view.*

class AdapterPartyNotVisitRecyclerView(var context: Context, var dataList:ArrayList<List_Party_Last20>):
    RecyclerView.Adapter<AdapterPartyNotVisitRecyclerView.AdapterPartyNotVisitViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPartyNotVisitViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_party_notvisited_list,parent,false)
        return AdapterPartyNotVisitViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterPartyNotVisitViewHolder, position: Int) {
        holder.shopNameTV.text=dataList.get(position).shop_name
        holder.shopTypeTv.text=dataList.get(position).shop_type
        holder.shopwiseLastOrderDt.text=dataList.get(position).last_order_date
        holder.shopwiseLastVisitDt.text=dataList.get(position).last_visit_date
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    inner class AdapterPartyNotVisitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val shopNameTV = itemView.tv_row_party_notviisted_listShopName
        val shopTypeTv = itemView.tv_row_party_notviisted_listShopType
        val shopwiseLastOrderDt = itemView.tv_row_party_notviisted_listlastOrderDt
        val shopwiseLastVisitDt = itemView.tv_row_party_notviisted_listlastvisitDt

    }

}