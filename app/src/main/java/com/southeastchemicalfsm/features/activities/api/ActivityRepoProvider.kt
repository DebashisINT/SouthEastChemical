package com.southeastchemicalfsm.features.activities.api

import com.southeastchemicalfsm.features.member.api.TeamApi
import com.southeastchemicalfsm.features.member.api.TeamRepo

object ActivityRepoProvider {
    fun activityRepoProvider(): ActivityRepo {
        return ActivityRepo(ActivityApi.create())
    }

    fun activityImageRepoProvider(): ActivityRepo {
        return ActivityRepo(ActivityApi.createImage())
    }
}