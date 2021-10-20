package com.example.adminshabu.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Food(
    @Expose
    @SerializedName("food_id") val food_id: Int,

    @Expose
    @SerializedName("food_name") val food_name: String,

    @Expose
    @SerializedName("food_img") val food_img: String,

    @Expose
    @SerializedName("food_amount") val food_amount: Int,

    @Expose
    @SerializedName("foodcategory_id") val foodcategory_id: Int
) {
}