package com.southeastchemicalfsm.features.survey.api

import com.southeastchemicalfsm.features.photoReg.api.GetUserListPhotoRegApi
import com.southeastchemicalfsm.features.photoReg.api.GetUserListPhotoRegRepository

object SurveyDataProvider{

    fun provideSurveyQ(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.create())
    }

    fun provideSurveyQMultiP(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.createImage())
    }
}