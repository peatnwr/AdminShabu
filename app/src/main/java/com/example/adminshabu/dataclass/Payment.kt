package com.example.adminshabu.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Payment(
    @Expose
    @SerializedName("cus_table") val cus_table: Int,

    @Expose
    @SerializedName("cus_amount") val cus_amount: Int,

    @Expose
    @SerializedName("order_id") val order_id: Int,

    @Expose
    @SerializedName("order_price") val order_price: Int,

    @Expose
    @SerializedName("cus_code") val cus_code: String
) {
}