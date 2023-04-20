package com.southeastchemicalfsm.features.weather.api

import com.southeastchemicalfsm.features.task.api.TaskApi
import com.southeastchemicalfsm.features.task.api.TaskRepo

object WeatherRepoProvider {
    fun weatherRepoProvider(): WeatherRepo {
        return WeatherRepo(WeatherApi.create())
    }
}