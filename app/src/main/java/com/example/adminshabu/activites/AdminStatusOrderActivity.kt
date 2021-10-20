package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminshabu.R
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.ActivityAdminStatusOrderBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.OrderDetail
import com.example.adminshabu.module.AdminStatusOrderAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminStatusOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminStatusOrderBinding
    var orderStatusList = arrayListOf<OrderDetail>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminStatusOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")

        binding.recyclerView.adapter = AdminStatusOrderAdapter(orderStatusList, empInfo, applicationContext)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        binding.btnBack.setOnClickListener {
            val mainActivity = Intent(this@AdminStatusOrderActivity, MainActivity::class.java)
            mainActivity.putExtra("empInfo", empInfo)
            startActivity(mainActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        callOrderStatus()
    }

    fun callOrderStatus(){
        orderStatusList.clear()
        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")
        val api: clientAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clientAPI::class.java)
        api.retrieveCookStatus().enqueue(object : Callback<List<OrderDetail>> {
            override fun onResponse(
                call: Call<List<OrderDetail>>,
                response: Response<List<OrderDetail>>
            ) {
                if(response.isSuccessful){
                    response.body()?.forEach {
                        orderStatusList.add(OrderDetail(it.cus_table, it.food_name, it.orderdetail_qty, it.orderdetail_id))
                    }
                    binding.recyclerView.adapter = AdminStatusOrderAdapter(orderStatusList, empInfo, applicationContext)
                }
            }

            override fun onFailure(call: Call<List<OrderDetail>>, t: Throwable) {
                return t.printStackTrace()
            }
        })
    }
}