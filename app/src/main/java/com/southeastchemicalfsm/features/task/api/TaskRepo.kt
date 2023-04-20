package com.southeastchemicalfsm.features.task.api

import com.southeastchemicalfsm.app.Pref
import com.southeastchemicalfsm.base.BaseResponse
import com.southeastchemicalfsm.features.task.model.AddTaskInputModel
import com.southeastchemicalfsm.features.task.model.TaskListResponseModel
import com.southeastchemicalfsm.features.timesheet.api.TimeSheetApi
import com.southeastchemicalfsm.features.timesheet.model.TimeSheetListResponseModel
import io.reactivex.Observable

/**
 * Created by Saikat on 12-Aug-20.
 */
class TaskRepo(val apiService: TaskApi) {
    fun addTask(addTask: AddTaskInputModel): Observable<BaseResponse> {
        return apiService.addTask(addTask)
    }

    fun editTask(addTask: AddTaskInputModel): Observable<BaseResponse> {
        return apiService.editTask(addTask)
    }

    fun updateStatus(id: String, isCompleted: Boolean): Observable<BaseResponse> {
        return apiService.updateStatus(Pref.session_token!!, Pref.user_id!!, id, isCompleted)
    }

    fun deleteTask(id: String): Observable<BaseResponse> {
        return apiService.deleteTask(Pref.session_token!!, Pref.user_id!!, id)
    }

    fun taskList(): Observable<TaskListResponseModel> {
        return apiService.taskList(Pref.session_token!!, Pref.user_id!!)
    }
}