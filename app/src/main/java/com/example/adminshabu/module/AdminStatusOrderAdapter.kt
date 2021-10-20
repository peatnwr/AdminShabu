package com.example.adminshabu.module

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminshabu.activites.AdminStatusOrderActivity
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.AdminStatusOrderItemLayoutBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.OrderDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminStatusOrderAdapter(val orderStatusList : ArrayList<OrderDetail>, val empInfo: EmployeeParcelable?, val context: Context) :RecyclerView.Adapter<AdminStatusOrderAdapter.ViewHolder>() {
    inner class ViewHolder(view: View, val binding: AdminStatusOrderItemLayoutBinding) :RecyclerView.ViewHolder(view){
        init {
            binding.btnConfirm.setOnClickListener {
                val item = orderStatusList[adapterPosition]
                val contextView : Context = view.context
                val api: clientAPI = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(clientAPI::class.java)
                api.updateCookStatus(
                    item.orderdetail_id
                ).enqueue(object : Callback<OrderDetail> {
                    override fun onResponse(
                        call: Call<OrderDetail>,
                        response: Response<OrderDetail>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(contextView, "Update Cook Status Successful", Toast.LENGTH_SHORT).show()
                            val statusOrderPage = Intent(contextView, AdminStatusOrderActivity::class.java)
                            statusOrderPage.putExtra("empInfo", empInfo)
                            contextView.startActivity(statusOrderPage)
                        }
                    }

                    override fun onFailure(call: Call<OrderDetail>, t: Throwable) {
                        return t.printStackTrace()
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdminStatusOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        binding.showTable.text = "โต๊ะ : " + orderStatusList!![position].cus_table.toString()
        binding.orderFood.text = "รายการอาหาร : " + orderStatusList!![position].food_name
        binding.orderAmount.text = "จำนวน : " + orderStatusList!![position].orderdetail_qty.toString()
    }

    override fun getItemCount(): Int {
        return orderStatusList!!.size
    }
}