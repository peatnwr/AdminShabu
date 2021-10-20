package com.example.adminshabu.api

import com.example.adminshabu.dataclass.*
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

    @GET("searchCategory/{foodcategory_name}")
    fun searchCategory(
        @Path("foodcategory_name") foodcategory_name: String
    ): Call<List<FoodCategory>>

    @GET("searchFood/{food_name}/{foodcategory_id}")
    fun searchFood(
        @Path("food_name") food_name: String,
        @Path("foodcategory_id") foodcategory_id: Int
    ): Call<List<Food>>

    @GET("cookStatus")
    fun retrieveCookStatus(): Call<List<OrderDetail>>

    @PUT("updateCookStatus/{orderdetail_id}")
    fun updateCookStatus(
        @Path("orderdetail_id") orderdetail_id: Int
    ): Call<OrderDetail>

    @GET("paymentStatus")
    fun retrievePaymentStatus(): Call<List<Payment>>

    @PUT("updatePaymentStatus/{order_id}/{cus_code}")
    @FormUrlEncoded
    fun updatePaymentStatus(
        @Path("order_id") order_id: Int,
        @Path("cus_code") cus_code: String,
        @Field("date") date: String
    ): Call<Payment>

    //authentication API
    @POST("loginEmp")
    @FormUrlEncoded
    fun loginEmp(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Employee>

    @PUT("editProfile/{emp_id}")
    @FormUrlEncoded
    fun editProfile(
        @Path("emp_id") emp_id: Int,
        @Field("emp_name") emp_name: String,
        @Field("emp_username") emp_username: String,
        @Field("emp_tel") emp_tel: String,
        @Field("emp_img") emp_img: String
    ): Call<Employee>
}