package com.southeastchemicalfsm.features.damageProduct.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.southeastchemicalfsm.app.FileUtils
import com.southeastchemicalfsm.base.BaseResponse
import com.southeastchemicalfsm.features.NewQuotation.model.*
import com.southeastchemicalfsm.features.addshop.model.AddShopRequestData
import com.southeastchemicalfsm.features.addshop.model.AddShopResponse
import com.southeastchemicalfsm.features.damageProduct.model.DamageProductResponseModel
import com.southeastchemicalfsm.features.damageProduct.model.delBreakageReq
import com.southeastchemicalfsm.features.damageProduct.model.viewAllBreakageReq
import com.southeastchemicalfsm.features.login.model.userconfig.UserConfigResponseModel
import com.southeastchemicalfsm.features.myjobs.model.WIPImageSubmit
import com.southeastchemicalfsm.features.photoReg.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class GetDamageProductListRegRepository(val apiService : GetDamageProductListApi) {

    fun viewBreakage(req: viewAllBreakageReq): Observable<DamageProductResponseModel> {
        return apiService.viewBreakage(req)
    }

    fun delBreakage(req: delBreakageReq): Observable<BaseResponse>{
        return apiService.BreakageDel(req.user_id!!,req.breakage_number!!,req.session_token!!)
    }

}