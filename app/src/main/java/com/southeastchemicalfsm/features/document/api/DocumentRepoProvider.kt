package com.southeastchemicalfsm.features.document.api

import com.southeastchemicalfsm.features.dymanicSection.api.DynamicApi
import com.southeastchemicalfsm.features.dymanicSection.api.DynamicRepo

object DocumentRepoProvider {
    fun documentRepoProvider(): DocumentRepo {
        return DocumentRepo(DocumentApi.create())
    }

    fun documentRepoProviderMultipart(): DocumentRepo {
        return DocumentRepo(DocumentApi.createImage())
    }
}