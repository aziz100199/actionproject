package com.unit.spinneractivity.retrofit.api

import com.unit.spinneractivity.retrofit.model.RetrofitModel
import retrofit2.Response
import retrofit2.http.GET

interface Todo {

    @GET("/todos")
   suspend fun getvalue(): Response<List<RetrofitModel>>

}