package com.example.adminshabu.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Customer(
    @Expose
    @SerializedName("cus_code") val cus_code: String,

    @Expose
    @SerializedName("cus_amount") val cus_amount: Int,

    @Expose
    @SerializedName("cus_table") val cus_table: Int
) {
}