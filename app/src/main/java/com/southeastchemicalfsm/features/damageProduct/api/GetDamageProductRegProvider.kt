package com.southeastchemicalfsm.features.damageProduct.api



object GetDamageProductRegProvider {

    fun provideSaveButton(): GetDamageProductListRegRepository {
        return GetDamageProductListRegRepository(GetDamageProductListApi.create())
    }
    fun provideDel(): GetDamageProductListRegRepository {
        return GetDamageProductListRegRepository(GetDamageProductListApi.createFacePic())
    }

}