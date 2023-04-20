package com.southeastchemicalfsm.features.lead.api

import com.southeastchemicalfsm.features.NewQuotation.api.GetQuotListRegRepository
import com.southeastchemicalfsm.features.NewQuotation.api.GetQutoListApi


object GetLeadRegProvider {
    fun provideList(): GetLeadListRegRepository {
        return GetLeadListRegRepository(GetLeadListApi.create())
    }
}