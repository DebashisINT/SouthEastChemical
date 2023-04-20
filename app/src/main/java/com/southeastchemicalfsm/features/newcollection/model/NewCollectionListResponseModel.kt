package com.southeastchemicalfsm.features.newcollection.model

import com.southeastchemicalfsm.app.domain.CollectionDetailsEntity
import com.southeastchemicalfsm.base.BaseResponse
import com.southeastchemicalfsm.features.shopdetail.presentation.model.collectionlist.CollectionListDataModel

/**
 * Created by Saikat on 15-02-2019.
 */
class NewCollectionListResponseModel : BaseResponse() {
    //var collection_list: ArrayList<CollectionListDataModel>? = null
    var collection_list: ArrayList<CollectionDetailsEntity>? = null
}