package com.example.adminshabu.api

import com.example.adminshabu.dataclass.Customer
import com.example.adminshabu.dataclass.Employee
import com.example.adminshabu.dataclass.Food
import com.example.adminshabu.dataclass.FoodCategory
import retrofit2.Call
import retrofit2.http.*

interface clientAPI {
    //main API
    @POST("createTable")
    @FormUrlEncoded
    fun createTable(
        @Field("table") table: Int,
        @Field("amountCus") amountCus: Int,
        @Field("codeTable") codeTable: String
    ): Call<Customer>

    @GET("categoryFood")
    fun retrieveCategory() :Call<List<FoodCategory>>

    @GET("food/{foodcategory_id}")
    fun retrieveFood(
        @Path("foodcategory_id") foodcategory_id: Int
    ): Call<List<Food>>

    @POST("insertFood/{foodcategory_id}")
    @FormUrlEncoded
    fun insertFood(
        @Path("foodcategory_id") foodcategory_id: Int,
        @Field("food_name") food_name: String,
        @Field("food_img") food_img: String,
        @Field("food_amount") food_amount: Int
    ): Call<Food>

    @PUT("editFood/{food_id}")
    @FormUrlEncoded
    fun editFood(
        @Path("food_id") food_id: Int,
        @Field("food_name") food_name: String,
        @Field("food_amount") food_amount: Int,
        @Field("food_img") food_img: String
    ): Call<Food>

    @DELETE("deleteFood/{food_id}")
    fun deleteFood(
        @Path("food_id") food_id: Int
    ): Call<Food>

    //authentication API
    @POST("loginEmp")
    @FormUrlEncoded
    fun loginEmp(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Employee>
}