package com.example.adminshabu.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Employee(
    @Expose
    @SerializedName("emp_id") val emp_id: Int,

    @Expose
    @SerializedName("emp_name") val emp_name: String,

    @Expose
    @SerializedName("emp_tel") val emp_tel: String,

    @Expose
    @SerializedName("emp_username") val emp_username: String,

    @Expose
    @SerializedName("emp_img") val emp_img: String,

    @Expose
    @SerializedName("emp_type") val emp_type: Int
) {
}