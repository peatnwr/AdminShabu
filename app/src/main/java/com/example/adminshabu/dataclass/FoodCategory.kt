package com.example.adminshabu.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FoodCategory(
    @Expose
    @SerializedName("foodcategory_id") val foodcategory_id: Int,

    @Expose
    @SerializedName("foodcategory_name") val foodcategory_name: String,

    @Expose
    @SerializedName("foodcategory_img") val foodcategory_img: String
) {
}