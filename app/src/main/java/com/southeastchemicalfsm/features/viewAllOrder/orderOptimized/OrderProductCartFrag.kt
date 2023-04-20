package com.southeastchemicalfsm.features.viewAllOrder.orderOptimized

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.southeastchemicalfsm.CustomStatic
import com.southeastchemicalfsm.R
import com.southeastchemicalfsm.app.AppDatabase
import com.southeastchemicalfsm.app.NetworkConstant
import com.southeastchemicalfsm.app.Pref
import com.southeastchemicalfsm.app.domain.AddShopDBModelEntity
import com.southeastchemicalfsm.app.domain.OrderDetailsListEntity
import com.southeastchemicalfsm.app.domain.OrderProductListEntity
import com.southeastchemicalfsm.app.types.FragType
import com.southeastchemicalfsm.app.utils.AppUtils
import com.southeastchemicalfsm.app.utils.Toaster
import com.southeastchemicalfsm.app.utils.ToasterMiddle
import com.southeastchemicalfsm.base.BaseResponse
import com.southeastchemicalfsm.base.presentation.BaseActivity
import com.southeastchemicalfsm.base.presentation.BaseFragment
import com.southeastchemicalfsm.features.commondialog.presentation.CommonDialog
import com.southeastchemicalfsm.features.commondialog.presentation.CommonDialogClickListener
import com.southeastchemicalfsm.features.dashboard.presentation.DashboardActivity
import com.southeastchemicalfsm.features.location.LocationWizard
import com.southeastchemicalfsm.features.viewAllOrder.AddRemarksSignDialog
import com.southeastchemicalfsm.features.viewAllOrder.ViewAllOrderListFragment
import com.southeastchemicalfsm.features.viewAllOrder.api.addorder.AddOrderRepoProvider
import com.southeastchemicalfsm.features.viewAllOrder.model.AddOrderInputParamsModel
import com.southeastchemicalfsm.features.viewAllOrder.model.AddOrderInputProductList
import com.southeastchemicalfsm.features.viewAllOrder.model.NewOrderCartModel
import com.southeastchemicalfsm.widgets.AppCustomTextView
import com.pnikosis.materialishprogress.ProgressWheel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class OrderProductCartFrag : BaseFragment(), View.OnClickListener{

    private lateinit var mContext: Context
    private lateinit var rv_prodL: RecyclerView
    private lateinit var cartAdapter : AdapterOrdCartOptimized
    private lateinit var tv_totalItem : TextView
    private lateinit var tv_totalAmt : TextView
    private lateinit var llPlaceOrder : LinearLayout
    private lateinit var progrwss_wheel: ProgressWheel

    private var remarksStr = ""
    private var imagePathStr = ""
    private var shopDtls = AddShopDBModelEntity()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    companion object {
        var cartInfo: FinalOrderDataWithShopID? = null
        var shop_id: String = ""
        //var cartOrdList: ArrayList<FinalOrderData>? = null
        fun getInstance(objects: Any): OrderProductCartFrag {
            val Fragment = OrderProductCartFrag()
            cartInfo = objects as FinalOrderDataWithShopID
            shop_id = cartInfo!!.shop_id
            //cartOrdList = cartInfo!!.ordList
            return Fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.order_product_cart_frag, container, false)
        initView(view)
        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initView(view: View?) {
        rv_prodL = view!!.findViewById(R.id.rv_ord_prod_cart_frag)
        tv_totalItem = view!!.findViewById(R.id.tv_ord_prod_cart_frag_total_item)
        tv_totalAmt = view!!.findViewById(R.id.tv_ord_prod_cart_frag_total_value)
        llPlaceOrder = view!!.findViewById(R.id.ll_ord_prod_cart_frag_place_order)
        progrwss_wheel = view!!.findViewById(R.id.pw_frag_ord_cart_list)
        progrwss_wheel.stopSpinning()


        llPlaceOrder.setOnClickListener(this)

        shopDtls = AppDatabase.getDBInstance()!!.addShopEntryDao().getShopByIdN(shop_id)

        try{
            var qtyRectify:Double = String.format("%.3f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.qty.toDouble() }).toDouble()
            if(qtyRectify-qtyRectify.toInt() == 0.0){
                tv_totalItem.text = qtyRectify.toInt().toString()
            }else{
                tv_totalItem.text = qtyRectify.toString()
            }
            tv_totalAmt.text = String.format("%.2f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.rate.toDouble() * it.qty.toDouble() }).toString()
        }catch (ex:Exception){
            ex.printStackTrace()
        }

        loadCartAdapter()
    }

    fun loadCartAdapter(){
        cartAdapter = AdapterOrdCartOptimized(mContext,OrderProductListFrag.finalOrderDataList!!,object :
            AdapterOrdCartOptimized.OnRateQtyOptiOnClick{
            override fun onRateChangeClick(productID: String, changingRate: String) {
                doAsync {
                    //OrderProductListFrag.finalOrderDataList!!.filter { it.product_id.equals(productID) }.first().rate = changingRate
                    uiThread {
                        try{
                            var qtyRectify:Double = String.format("%.3f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.qty.toDouble() }).toDouble()
                            if(qtyRectify-qtyRectify.toInt() == 0.0){
                                tv_totalItem.text = qtyRectify.toInt().toString()
                            }else{
                                tv_totalItem.text = qtyRectify.toString()
                            }
                            tv_totalAmt.text = String.format("%.2f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.rate.toDouble() * it.qty.toDouble() }).toString()
                        }catch (ex:Exception){
                            ex.printStackTrace()
                        }
                    }
                }
            }
            override fun onQtyChangeClick(productID: String, changingQty: String) {
                doAsync {
                    //OrderProductListFrag.finalOrderDataList!!.filter { it.product_id.equals(productID) }.first().qty = changingQty
                    uiThread {
                        try{
                            var qtyRectify:Double = String.format("%.3f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.qty.toDouble() }).toDouble()
                            if(qtyRectify-qtyRectify.toInt() == 0.0){
                                tv_totalItem.text = qtyRectify.toInt().toString()
                            }else{
                                tv_totalItem.text = qtyRectify.toString()
                            }
                            tv_totalAmt.text = String.format("%.2f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.rate.toDouble() * it.qty.toDouble() }).toString()
                        }catch (ex:Exception){
                            ex.printStackTrace()
                        }
                    }
                }
            }
            override fun onDiscountChangeClick(productID: String, changingDisc: String,changingRate: String) {
                doAsync {
                    /*OrderProductListFrag.finalOrderDataList!!.filter { it.product_id.equals(productID) }.first().apply {
                        product_discount_show = changingDisc
                        rate = changingRate
                    }*/
                    uiThread {
                        try{
                            var qtyRectify:Double = String.format("%.3f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.qty.toDouble() }).toDouble()
                            if(qtyRectify-qtyRectify.toInt() == 0.0){
                                tv_totalItem.text = qtyRectify.toInt().toString()
                            }else{
                                tv_totalItem.text = qtyRectify.toString()
                            }
                            tv_totalAmt.text = String.format("%.2f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.rate.toDouble() * it.qty.toDouble() }).toString()
                        }catch (ex:Exception){
                            ex.printStackTrace()
                        }
                    }
                }
            }

            override fun onDelChangeClick(cartSize: Int) {
                var qtyRectify:Double = String.format("%.3f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.qty.toDouble() }).toDouble()
                if(qtyRectify-qtyRectify.toInt() == 0.0){
                    tv_totalItem.text = qtyRectify.toInt().toString()
                }else{
                    tv_totalItem.text = qtyRectify.toString()
                }
                tv_totalAmt.text = String.format("%.2f",OrderProductListFrag.finalOrderDataList!!.sumByDouble { it.rate.toDouble() * it.qty.toDouble() }).toString()
            }
        })
        rv_prodL.adapter = cartAdapter
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_ord_prod_cart_frag_place_order ->{
                if(OrderProductListFrag.finalOrderDataList!!.size>0 ){
                    progrwss_wheel.spin()

                    var isValidQty = true
                    var isValidRate = true
                    for(i in 0..OrderProductListFrag.finalOrderDataList!!.size-1){
                            if(OrderProductListFrag.finalOrderDataList!!.get(i).qty.toDouble() == 0.0){
                                isValidQty = false
                                break
                            }
                            if(OrderProductListFrag.finalOrderDataList!!.get(i).rate.toDouble() == 0.0){
                                isValidRate = false
                                break
                            }
                        }
                    if(!isValidQty) {
                        progrwss_wheel.stopSpinning()
                        ToasterMiddle.msgShort(mContext,"Please enter valid quantity.")
                        return
                    }
                    else if(!isValidRate){
                        progrwss_wheel.stopSpinning()
                        ToasterMiddle.msgShort(mContext,"Please enter valid Rate.")
                        return
                    }
                    showCheckAlert("Order Confirmation", "Would you like to confirm the order?")
                }
            }
        }
    }

    private fun saveOrder(){
        Handler().postDelayed(Runnable {
            if(tv_totalAmt.text.toString().toDouble() !=0.0){
                val orderListDetails = OrderDetailsListEntity()
                orderListDetails.amount = tv_totalAmt.text.toString()
                orderListDetails.description = ""
                orderListDetails.collection = ""
                orderListDetails.scheme_amount = ""
                val list = AppDatabase.getDBInstance()!!.orderDetailsListDao().getListAccordingDate(AppUtils.getCurrentDate())
                if (list == null || list.isEmpty()) {
                    orderListDetails.order_id = Pref.user_id + AppUtils.getCurrentDateMonth() + "0001"
                } else {
                    val lastId = list[0].order_id?.toLong()
                    val finalId = lastId!! + 1
                    orderListDetails.order_id = finalId.toString()
                }
                orderListDetails.shop_id = shop_id
                orderListDetails.date = AppUtils.getCurrentISODateTime()
                orderListDetails.only_date = AppUtils.getCurrentDate()

                orderListDetails.remarks = remarksStr
                orderListDetails.signature = imagePathStr

                orderListDetails.patient_name = ""
                orderListDetails.patient_address = ""
                orderListDetails.patient_no = ""
                orderListDetails.Hospital = ""
                orderListDetails.Email_Address = ""

                orderListDetails.order_lat = Pref.current_latitude
                orderListDetails.order_long = Pref.current_longitude
                orderListDetails.isUploaded = false

                AppDatabase.getDBInstance()!!.orderDetailsListDao().insert(orderListDetails)

                doAsync {
                    val productOrderList : ArrayList<OrderProductListEntity> = ArrayList()
                    for(i in 0..OrderProductListFrag.finalOrderDataList!!.size-1){
                        var obj = OrderProductListEntity()
                        obj.product_id = OrderProductListFrag.finalOrderDataList!!.get(i).product_id
                        obj.product_name = OrderProductListFrag.finalOrderDataList!!.get(i).product_name
                        obj.brand_id = OrderProductListFrag.finalOrderDataList!!.get(i).brand_id
                        obj.brand = OrderProductListFrag.finalOrderDataList!!.get(i).brand
                        obj.category_id = OrderProductListFrag.finalOrderDataList!!.get(i).category_id
                        obj.category = OrderProductListFrag.finalOrderDataList!!.get(i).category
                        obj.watt_id = OrderProductListFrag.finalOrderDataList!!.get(i).watt_id
                        obj.watt = OrderProductListFrag.finalOrderDataList!!.get(i).watt
                        obj.qty = OrderProductListFrag.finalOrderDataList!!.get(i).qty
                        obj.rate = OrderProductListFrag.finalOrderDataList!!.get(i).rate
                        obj.total_price = String.format("%.2f",(OrderProductListFrag.finalOrderDataList!!.get(i).rate.toDouble() * OrderProductListFrag.finalOrderDataList!!.get(i).qty.toDouble())).toString()
                        obj.order_mrp = OrderProductListFrag.finalOrderDataList!!.get(i).product_mrp_show
                        obj.order_discount = OrderProductListFrag.finalOrderDataList!!.get(i).product_discount_show
                        obj.order_id = orderListDetails.order_id
                        obj.shop_id = orderListDetails.shop_id

                        productOrderList.add(obj)
                    }
                    AppDatabase.getDBInstance()!!.orderProductListDao().insertAll(productOrderList)
                    uiThread {
                        progrwss_wheel.stopSpinning()
                        if(shopDtls.isUploaded && AppUtils.isOnline(mContext)){
                            syncOrder(orderListDetails,productOrderList)
                        }else{
                            msgShow("${AppUtils.hiFirstNameText()}. Your order for ${shopDtls.shopName} has been placed successfully.Order No. is ${orderListDetails.order_id}")
                        }

                    }
                }
            }
        }, 1000)
    }

    private fun syncOrder(orderObj : OrderDetailsListEntity,orderProductList : ArrayList<OrderProductListEntity>){
        progrwss_wheel.spin()
        val addOrderApiObj = AddOrderInputParamsModel()
        addOrderApiObj.product_list = ArrayList()

        addOrderApiObj.session_token = Pref.session_token
        addOrderApiObj.user_id = Pref.user_id
        addOrderApiObj.order_amount = orderObj.amount
        addOrderApiObj.shop_id = shop_id
        addOrderApiObj.order_id = orderObj.order_id
        addOrderApiObj.description = ""
        addOrderApiObj.collection = "0"
        addOrderApiObj.order_date = orderObj.date
        addOrderApiObj.latitude = orderObj.order_lat
        addOrderApiObj.longitude = orderObj.order_long
        addOrderApiObj.address = LocationWizard.getLocationName(mContext, orderObj.order_lat!!.toDouble(), orderObj.order_long!!.toDouble())
        addOrderApiObj.remarks = remarksStr
        addOrderApiObj.patient_no = ""
        addOrderApiObj.patient_name = ""
        addOrderApiObj.patient_address = ""
        addOrderApiObj.scheme_amount = "0"
        addOrderApiObj.Hospital = ""
        addOrderApiObj.Email_Address = ""
        for(i in 0..orderProductList.size-1){
            var productObj = AddOrderInputProductList()
            productObj.apply {
                id = orderProductList.get(i).product_id!!.toString()
                qty = orderProductList.get(i).qty
                rate = orderProductList.get(i).rate
                total_price = orderProductList.get(i).total_price
                product_name = orderProductList.get(i).product_name
                scheme_qty = orderProductList.get(i).scheme_qty
                scheme_rate = orderProductList.get(i).scheme_rate
                total_scheme_price = orderProductList.get(i).total_scheme_price
                MRP = "0"
                order_mrp = orderProductList.get(i).order_mrp
                order_discount = orderProductList.get(i).order_discount
            }
            addOrderApiObj.product_list!!.add(productObj)
        }

        if (TextUtils.isEmpty(imagePathStr)) {
            val repository = AddOrderRepoProvider.provideAddOrderRepository()
            BaseActivity.compositeDisposable.add(
                repository.addNewOrder(addOrderApiObj)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        val orderList = result as BaseResponse
                        progrwss_wheel.stopSpinning()
                        if (orderList.status == NetworkConstant.SUCCESS) {
                            AppDatabase.getDBInstance()!!.orderDetailsListDao().updateIsUploaded(true, addOrderApiObj.order_id!!)
                        }
                        (mContext as DashboardActivity).showSnackMessage("Order added successfully")
                        msgShow("${AppUtils.hiFirstNameText()}. Your order for ${shopDtls.shopName} has been placed successfully.Order No. is ${addOrderApiObj.order_id}")
                    }, { error ->
                        error.printStackTrace()
                        progrwss_wheel.stopSpinning()
                        (mContext as DashboardActivity).showSnackMessage("Error.")
                    })
            )
        }
        else {
            val repository = AddOrderRepoProvider.provideAddOrderImageRepository()
            BaseActivity.compositeDisposable.add(
                repository.addNewOrder(addOrderApiObj, imagePathStr!!, mContext)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        val orderList = result as BaseResponse
                        progrwss_wheel.stopSpinning()
                        if (orderList.status == NetworkConstant.SUCCESS) {
                            AppDatabase.getDBInstance()!!.orderDetailsListDao().updateIsUploaded(true, addOrderApiObj.order_id!!)
                        }
                        (mContext as DashboardActivity).showSnackMessage("Order added successfully")
                        msgShow("${AppUtils.hiFirstNameText()}. Your order for ${shopDtls.shopName} has been placed successfully.Order No. is ${addOrderApiObj.order_id}")
                    }, { error ->
                        error.printStackTrace()
                        progrwss_wheel.stopSpinning()
                        (mContext as DashboardActivity).showSnackMessage("Error.")
                    })
            )
        }

    }

    private fun showCheckAlert(header: String, title: String) {
        CommonDialog.getInstance(header, title, getString(R.string.no), getString(R.string.yes), false, object :
            CommonDialogClickListener {
            override fun onLeftClick() {
                progrwss_wheel.stopSpinning()
            }
            override fun onRightClick(editableData: String) {
                if (!Pref.isShowOrderRemarks && !Pref.isShowOrderSignature)
                    saveOrder()
                else
                    showRemarksAlert()
            }
        }).show((mContext as DashboardActivity).supportFragmentManager, "")
    }

    private fun showRemarksAlert() {
        AddRemarksSignDialog.getInstance(remarksStr, imagePathStr, { remark, imgPath ->
            remarksStr = remark
            imagePathStr = imgPath
            saveOrder()
        }, {
            saveOrder()
        }).show((mContext as DashboardActivity).supportFragmentManager, "")
    }

    fun msgShow(msg:String){
        val simpleDialog = Dialog(mContext)
        simpleDialog.setCancelable(false)
        simpleDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        simpleDialog.setContentView(R.layout.dialog_message)
        val headerTv = simpleDialog.findViewById(R.id.dialog_message_headerTV) as AppCustomTextView
        val bodyTv = simpleDialog.findViewById(R.id.dialog_message_header_TV) as AppCustomTextView
        val okTV = simpleDialog.findViewById(R.id.tv_message_ok) as AppCustomTextView
        headerTv.text = "Congrats!"
        bodyTv.text = msg
        okTV.setOnClickListener({ view ->
            simpleDialog.cancel()
            CustomStatic.IsBackFromNewOptiCart=true
            (mContext as DashboardActivity).loadFragment(FragType.ViewAllOrderListFragment,false,shopDtls)
        })
        simpleDialog.show()
        voiceOrderMsg()
    }

    private fun voiceOrderMsg() {
        if (Pref.isVoiceEnabledForOrderSaved) {
            val msg = "Hi, Order saved successfully."
            val speechStatus = (mContext as DashboardActivity).textToSpeech.speak(
                msg,
                TextToSpeech.QUEUE_FLUSH,
                null
            )
            if (speechStatus == TextToSpeech.ERROR)
                Log.e("Add Order", "TTS error in converting Text to Speech!")
        }
    }

}