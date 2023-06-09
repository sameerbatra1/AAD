package com.example.exampractice.network

import com.example.exampractice.contract.Request
import com.example.exampractice.contract.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IRequestContract {
    @POST("service.php")
    fun makeApiCall(@Body request:Request):Call<Response>
}