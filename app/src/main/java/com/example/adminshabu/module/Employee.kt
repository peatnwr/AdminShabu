package com.example.adminshabu.module

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
    @SerializedName("emp_password") val emp_password: String,

    @Expose
    @SerializedName("emp_username") val emp_username: String
) {
}