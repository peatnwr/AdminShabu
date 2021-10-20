package com.example.adminshabu.dataclass

import android.os.Parcel
import android.os.Parcelable

data class EmployeeParcelable(val emp_id: Int, val emp_username: String, val emp_name: String, val emp_tel: String, val emp_img: String, val emp_type: Int) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(emp_id)
        parcel.writeString(emp_username)
        parcel.writeString(emp_name)
        parcel.writeString(emp_tel)
        parcel.writeString(emp_img)
        parcel.writeInt(emp_type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmployeeParcelable> {
        override fun createFromParcel(parcel: Parcel): EmployeeParcelable {
            return EmployeeParcelable(parcel)
        }

        override fun newArray(size: Int): Array<EmployeeParcelable?> {
            return arrayOfNulls(size)
        }
    }
}