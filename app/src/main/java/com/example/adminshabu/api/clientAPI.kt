package com.example.adminshabu.api

import com.example.adminshabu.module.Employee
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface clientAPI {
    @POST("loginEmp")
    @FormUrlEncoded
    fun loginEmp(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Employee>
}