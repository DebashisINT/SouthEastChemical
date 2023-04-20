package com.southeastchemicalfsm.features.weather.api

import com.southeastchemicalfsm.base.BaseResponse
import com.southeastchemicalfsm.features.task.api.TaskApi
import com.southeastchemicalfsm.features.task.model.AddTaskInputModel
import com.southeastchemicalfsm.features.weather.model.ForeCastAPIResponse
import com.southeastchemicalfsm.features.weather.model.WeatherAPIResponse
import io.reactivex.Observable

class WeatherRepo(val apiService: WeatherApi) {
    fun getCurrentWeather(zipCode: String): Observable<WeatherAPIResponse> {
        return apiService.getTodayWeather(zipCode)
    }

    fun getWeatherForecast(zipCode: String): Observable<ForeCastAPIResponse> {
        return apiService.getForecast(zipCode)
    }
}