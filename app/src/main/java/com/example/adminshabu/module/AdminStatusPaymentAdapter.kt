package com.example.adminshabu.module

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminshabu.activites.AdminStatusPaymentActivity
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.AdminStatusPaymentItemLayoutBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.Payment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdminStatusPaymentAdapter(val paymentStatusList: ArrayList<Payment>, val empInfo: EmployeeParcelable?, val context: Context) :RecyclerView.Adapter<AdminStatusPaymentAdapter.ViewHolder>() {
    inner class ViewHolder(view: View, val binding: AdminStatusPaymentItemLayoutBinding) :RecyclerView.ViewHolder(view){
        init {
            binding.btnConfirm.setOnClickListener {
                val item = paymentStatusList[adapterPosition]
                val contextView : Context = view.context
                val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                val api: clientAPI = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(clientAPI::class.java)
                api.updatePaymentStatus(
                    item.order_id,
                    item.cus_code,
                    currentDate.toString()
                ).enqueue(object : Callback<Payment>{
                    override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                        if(response.isSuccessful){
                            Toast.makeText(contextView, "Update Payment Status Successful", Toast.LENGTH_SHORT).show()
                            val statusPaymentPage = Intent(contextView, AdminStatusPaymentActivity::class.java)
                            statusPaymentPage.putExtra("empInfo", empInfo)
                            contextView.startActivity(statusPaymentPage)
                        }
                    }

                    override fun onFailure(call: Call<Payment>, t: Throwable) {
                        return t.printStackTrace()
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdminStatusPaymentItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        binding.showTable.text = "โต๊ะ : " + paymentStatusList!![position].cus_table
        binding.cusAmount.text = "จำนวนลูกค้า : " + paymentStatusList!![position].cus_amount
        binding.sumPrice.text = "รวมราคาทั้งสิ้น : " + paymentStatusList!![position].order_price + " บาท"
    }

    override fun getItemCount(): Int {
        return paymentStatusList!!.size
    }
}