package com.example.adminshabu.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrderDetail(
    @Expose
    @SerializedName("cus_table") val cus_table: Int,

    @Expose
    @SerializedName("food_name") val food_name: String,

    @Expose
    @SerializedName("orderdetail_qty") val orderdetail_qty: Int,

    @Expose
    @SerializedName("orderdetail_id") val orderdetail_id: Int
) {
}